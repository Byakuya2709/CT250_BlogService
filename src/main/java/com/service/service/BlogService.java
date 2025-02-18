/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.service.service;

import com.service.exception.BlogNotFoundException;
import com.service.model.Blog;
import com.service.model.BlogEmotion;
import com.service.model.Comment;
import com.service.repository.BlogEmotionRepository;
import com.service.repository.BlogRepository;
import com.service.repository.CommentRepository;
import com.service.request.BlogDTO;
import com.service.request.CommentDTO;
import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
