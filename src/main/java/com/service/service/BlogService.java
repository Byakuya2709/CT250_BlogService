/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.service.service;

import com.service.exception.BadRequestException;
import com.service.exception.BlogNotFoundException;
import com.service.model.Blog;
import com.service.model.BlogEmotion;
import com.service.model.Comment;
import com.service.repository.BlogEmotionRepository;
import com.service.repository.BlogRepository;
import com.service.repository.CommentRepository;
import com.service.request.BlogDTO;
import com.service.request.CommentDTO;
import com.service.request.EditBlog;
import com.service.request.UpdateComment;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author admin
 */
@Service
public class BlogService {
    
    @Autowired
    private BlogRepository blogRepository;
    
    @Autowired
    private CommentRepository commentRepository;
    
    @Autowired
    private BlogEmotionRepository blogEmotionRepository;
    
    public Blog uploadBlog(BlogDTO blogDTO) {
        // Tạo đối tượng Blog từ BlogDTO
        Blog blog = new Blog();
        
        blog.setBlogSubject(blogDTO.getBlogSubject());
        blog.setBlogContent(blogDTO.getBlogContent());
        blog.setBlogType(blogDTO.getBlogType());
        
        blog.setEventListImgURL(blogDTO.getEventListImgURL());
        blog.setBlogCreateDate(new Date());
        blog.setBlogUpdateDate(new Date());
        blog.setBlogEmotionsNumber(blogDTO.getBlogEmotionsNumber());
        
        blog.setBlogUserId(blogDTO.getBlogUserId()); // Giả sử userId đã có sẵn
        blog.setEventId(blogDTO.getEventId());
        // Lưu blog vào MongoDB
        return blogRepository.save(blog);
    }
    
    public Comment postComment(CommentDTO dto, String blogId) {
        Comment cmt = new Comment();
        cmt.setCmtContent(dto.getCmtContent());
        cmt.setCmtCreateDate(new Date());
        cmt.setCmtEmotionsNumber(dto.getCmtEmotionsNumber());
        cmt.setCmtUserId(dto.getCmtUserId());
        cmt.setBlogId(blogId);
        
        return commentRepository.save(cmt);
    }
    
    public Blog getBlogById(String blogId) {
        return blogRepository.findById(blogId).orElseThrow(() -> new BlogNotFoundException("Blog not found"));
    }
    public Comment getCommentById(String commentId) {
        return commentRepository.findById(commentId).orElseThrow(() -> new BlogNotFoundException("Comment not found"));
    }
    
    public Page<Comment> getAllCommentOfBlog(String blogId, Pageable pageable) {
        return commentRepository.findByBlogId(blogId, pageable);
    }
    
    public void toggleEmotion(String blogId, String userId) {
        Optional<BlogEmotion> existingEmotion = blogEmotionRepository.findByBlogIdAndUserId(blogId, userId);
        
        if (existingEmotion.isPresent()) {
            BlogEmotion blogEmotion = existingEmotion.get();
            if (blogEmotion.isLiked()) {
                // Nếu đã "thích", bỏ thích
                blogEmotion.setLiked(false);
                // Giảm số cảm xúc
                Blog blog = blogRepository.findById(blogId).orElseThrow(() -> new BlogNotFoundException("Blog not found"));
                blog.setBlogEmotionsNumber(blog.getBlogEmotionsNumber() - 1);
                blogRepository.save(blog);
            } else {
                // Nếu chưa "thích", thích
                blogEmotion.setLiked(true);
                // Tăng số cảm xúc
                Blog blog = blogRepository.findById(blogId).orElseThrow(() -> new BlogNotFoundException("Blog not found"));
                blog.setBlogEmotionsNumber(blog.getBlogEmotionsNumber() + 1);
                blogRepository.save(blog);
            }
            // Cập nhật trạng thái cảm xúc của người dùng
            blogEmotionRepository.save(blogEmotion);
        } else {
            // Nếu người dùng chưa "thích", tạo mới trạng thái "thích"
            BlogEmotion blogEmotion = new BlogEmotion(blogId, userId, true);
            blogEmotionRepository.save(blogEmotion);

            // Tăng số cảm xúc
            Blog blog = blogRepository.findById(blogId).orElseThrow(() -> new BlogNotFoundException("Blog not found"));
            blog.setBlogEmotionsNumber(blog.getBlogEmotionsNumber() + 1);
            blogRepository.save(blog);
        }
    }
    
    public List<Blog> getRecentBlogs() {
        return blogRepository.findTop10ByOrderByBlogCreateDateDesc();
    }
    
    public Page<Blog> getBlogs(Long eventId, String userId, Integer month, Integer year, Pageable pageable) {
        boolean hasUserId = userId != null && !userId.trim().isEmpty();
        boolean hasEventId = eventId != null; // Không dùng .trim().isEmpty vì eventId là Long
        boolean hasMonth = month != null;
        boolean hasYear = year != null;
        
        System.out.println("eventId: " + eventId + ", userId: " + userId + ", month: " + month + ", year: " + year);
        
        if (hasEventId && hasUserId && hasMonth && hasYear) {
            return blogRepository.findByEventIdAndBlogUserIdAndMonthAndYear(eventId, userId, month, year, pageable);
        } else if (hasEventId && hasMonth && hasYear) {
            return blogRepository.findByEventIdAndMonthAndYear(eventId, month, year, pageable);
        } else if (hasUserId && hasMonth && hasYear) {
            return blogRepository.findByBlogUserIdAndMonthAndYear(userId, month, year, pageable);
        } else if (hasMonth && hasYear) {
            return blogRepository.findByMonthAndYear(month, year, pageable);
        } else if (hasEventId && hasUserId) {
            return blogRepository.findByEventIdAndBlogUserId(eventId, userId, pageable);
        } else if (hasEventId) {
            return blogRepository.findByEventId(eventId, pageable);
        } else if (hasUserId) {
            return blogRepository.findByBlogUserId(userId, pageable);
        } else {
            return blogRepository.findAll(pageable);
        }
    }
    
    @Transactional
    public void deleteBlogAndCommentRelated(String blogId) {
        try {
            // Kiểm tra blog có tồn tại trước khi xóa
            if (!blogRepository.existsById(blogId)) {
                throw new BadRequestException("Blog not found");
            }

            // Xóa tất cả các comment liên quan
            commentRepository.deleteByBlogId(blogId);

            // Xóa blog
            blogRepository.deleteById(blogId);
            
        } catch (DataAccessException e) {
            throw new RuntimeException("Error while deleting blog and related comments");
        }
    }
    
    public Blog updateCurrentBlog(String blogId, EditBlog req) {
        try {
            Blog blog = this.getBlogById(blogId);
            blog.setBlogContent(req.getBlogContent());
            blog.setBlogSubject(req.getBlogSubject());
            blog.setBlogType(req.getBlogType());
            
            return blogRepository.save(blog);
            
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    public void deleteComment(String commentId){
        commentRepository.deleteById(commentId);
    }
    @Transactional
    public Comment updateComment(String commentId, UpdateComment req){
        Comment cmt = this.getCommentById(commentId);
        
        if (!cmt.getCmtUserId().equals(req.getUserId()))  throw new RuntimeException("Chỉ người viết mới bình luận mới được cập nhật");
        cmt.setCmtContent(req.getCmtContent());
        return commentRepository.save(cmt);
    }
}
