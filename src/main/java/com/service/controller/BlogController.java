/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.service.controller;

import com.service.exception.BlogNotFoundException;
import com.service.exception.ResponseHandler;
import com.service.model.Blog;
import com.service.model.BlogEmotion;
import com.service.model.Comment;
import com.service.model.FeedBack;
import com.service.repository.BlogEmotionRepository;
import com.service.repository.BlogRepository;
import com.service.request.BlogDTO;
import com.service.request.CommentDTO;
import com.service.response.OneBlogResponse;
import com.service.service.BlogService;
import com.service.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author admin
 */
@RestController
@RequestMapping("/blog")
public class BlogController {
    
    @Autowired
    private BlogService blogService;

    @Autowired
    private FeedbackService feedbackService;

    //thuy
    @Autowired
    private BlogEmotionRepository blogEmotionRepository;

    //lấy tất cả
    @GetMapping("")
    public ResponseEntity<List<Blog>> getAllBlogs() {
        List<Blog> blogs = blogService.findAllBlogs();
        return ResponseEntity.ok(blogs);
    }

    //tạo bài viết - thuy
    @PostMapping("/{userId}/upload")
    public ResponseEntity<?> uploadBlog(@PathVariable String userId, @RequestBody BlogDTO blogDTO) {
        System.out.println("userId: " + userId );
        try {
            // Gọi service để lưu blog với userId từ đường dẫn
            Blog savedBlog = blogService.uploadBlog(blogDTO, userId);
            if (savedBlog != null) {
                // Trả về response thành công với blog đã lưu
                return ResponseHandler.resBuilder("Tạo blog thành công", HttpStatus.CREATED, savedBlog);
            }
        } catch (Exception e) {
            return ResponseHandler.resBuilder("Lỗi xảy ra trong quá trình tạo blog: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
        return ResponseHandler.resBuilder("Lỗi xảy ra trong quá trình tạo blog", HttpStatus.INTERNAL_SERVER_ERROR, null);
    }
    //

//    @GetMapping("")
//    public ResponseEntity<String> getBlog() {
//        return ResponseEntity.ok("Blog data here");
//    }

    @GetMapping("/by-user")
    public ResponseEntity<?> getBlogsByUser(@RequestParam String userId) {
        try {
            List<Blog> blogs = blogService.getBlogsByUser(userId);
            return ResponseHandler.resBuilder("Lấy danh sách bài viết thành công.", HttpStatus.OK, blogs);
        } catch (Exception e) {
            return ResponseHandler.resBuilder("Có lỗi xảy ra khi lấy danh sách bài viết: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    //lấy một bài viết - thuy
    @GetMapping("/{blogId}")
    public ResponseEntity<?> getEventById(@PathVariable String blogId) {
        try {
            Blog blog = blogService.getBlogById(blogId);
            if (blog != null) {
                return ResponseHandler.resBuilder("Lấy thông tin bài viết thành công", HttpStatus.OK, OneBlogResponse.toBlogResponse(blog));
            } else {
                return ResponseHandler.resBuilder("Không tìm thấy bài viết", HttpStatus.NOT_FOUND, null);
            }
        } catch (BlogNotFoundException e) {
            return ResponseHandler.resBuilder("bài viết không tồn tại: " + e.getMessage(), HttpStatus.NOT_FOUND, null);
        } catch (Exception e) {
            return ResponseHandler.resBuilder("Lỗi xảy ra trong quá trình lấy bài viết: " + e.getMessage().substring(0, 100), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

//    @PostMapping("/upload")
//    public ResponseEntity<?> uploadBlog(@RequestBody BlogDTO blogDTO) {
//        try {
//            Blog blog = blogService.uploadBlog(blogDTO);
//            return ResponseHandler.resBuilder("Tạo bài viết thành công.", HttpStatus.CREATED, blog);
//        } catch (Exception e) {
//            return ResponseHandler.resBuilder("Có lỗi xảy ra khi tạo bài viết." + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
//        }
//    }

    //tạo comment
    @PostMapping("/{blogId}/post-comment")
    public ResponseEntity<?> uploadBlogComment(@RequestBody CommentDTO commentDTO, @PathVariable String blogId) {
        try {
            Comment cmt = blogService.postComment(commentDTO, blogId);
            return ResponseHandler.resBuilder("Tạo bình luận bài viết thành công.", HttpStatus.CREATED, cmt);
        } catch (Exception e) {
            return ResponseHandler.resBuilder("Có lỗi xảy ra khi tạo bài viết." + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }


    //lấy comment
    @GetMapping("/{blogId}/comments")
    public ResponseEntity<?> getCommentsByBlogId(@PathVariable String blogId) {
        try {
            List<Comment> comments = blogService.getCommentsByBlogId(blogId);
            return ResponseHandler.resBuilder("Lấy bình luận thành công", HttpStatus.OK, comments);
        } catch (Exception e) {
            return ResponseHandler.resBuilder("Lỗi xảy ra trong quá trình lấy bình luận: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    //xóa bình luận
    @DeleteMapping("/{blogId}/comments/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable String blogId, @PathVariable String commentId) {
        try {
            blogService.deleteComment(blogId, commentId);
            return ResponseHandler.resBuilder("Xóa bình luận thành công.", HttpStatus.OK, null);
        } catch (Exception e) {
            return ResponseHandler.resBuilder("Có lỗi xảy ra khi xóa bình luận." + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    //chỉnh sửa bình luận
    @PutMapping("/{blogId}/comments/{commentId}")
    public ResponseEntity<?> updateComment(@PathVariable String blogId, @PathVariable String commentId, @RequestBody CommentDTO commentDTO) {
        try {
            Comment updatedComment = blogService.updateComment(blogId, commentId, commentDTO);
            return ResponseHandler.resBuilder("Chỉnh sửa bình luận thành công.", HttpStatus.OK, updatedComment);
        } catch (Exception e) {
            return ResponseHandler.resBuilder("Có lỗi xảy ra khi chỉnh sửa bình luận." + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    //emotion
//    @PostMapping("/{blogId}/emotion")
//    public ResponseEntity<Blog> toggleEmotion(@PathVariable String blogId, @RequestParam String userId) {
//        blogService.toggleEmotion(blogId, userId);
//        Blog updatedBlog = blogService.getBlogById(blogId);
//        return ResponseEntity.ok(updatedBlog);
//    }

    //sửa emotion
    @PostMapping("/{blogId}/emotion")
    public ResponseEntity<Map<String, Object>> toggleEmotion(@PathVariable String blogId, @RequestParam String userId) {
        blogService.toggleEmotion(blogId, userId);
        Blog updatedBlog = blogService.getBlogById(blogId);

        // Trả về trạng thái isLiked cùng với blogEmotionsNumber
        Map<String, Object> response = new HashMap<>();
        response.put("isLiked", blogEmotionRepository.findByBlogIdAndUserId(blogId, userId).get().isLiked());
        response.put("blogEmotionsNumber", updatedBlog.getBlogEmotionsNumber());

        return ResponseEntity.ok(response);
    }

    //thêm để kiểm tra đã tim
    @GetMapping("/{blogId}/check-liked")
    public ResponseEntity<Map<String, Object>> checkIfLiked(@PathVariable String blogId, @RequestParam String userId) {
        boolean isLiked = blogEmotionRepository.findByBlogIdAndUserId(blogId, userId).orElse(new BlogEmotion()).isLiked();
        Map<String, Object> response = new HashMap<>();
        response.put("isLiked", isLiked);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/with-likes")
    public ResponseEntity<List<Map<String, Object>>> getAllBlogsWithLikes(@RequestParam String userId) {
        List<Blog> blogs = blogService.findAllBlogs();
        List<Map<String, Object>> response = blogs.stream().map(blog -> {
            boolean isLiked = blogEmotionRepository.findByBlogIdAndUserId(blog.getBlogId(), userId).orElse(new BlogEmotion()).isLiked();
            Map<String, Object> blogData = new HashMap<>();
            blogData.put("blog", blog);
            blogData.put("isLiked", isLiked);
            return blogData;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    //thuy
    @PostMapping("/feedback/create")
    public ResponseEntity<Object> createFeedBack(@RequestBody FeedBack feedBack) {
        return feedbackService.createFeedBack(feedBack);
    }

    @GetMapping("/feedback/all")
    public ResponseEntity<Object> getAllFeedBacks() {
        return feedbackService.getAllFeedBacks();
    }

    @GetMapping("/feedback/{id}")
    public ResponseEntity<Object> getFeedBackById(@PathVariable String id) {
        return feedbackService.getFeedBackById(id);
    }

    @PutMapping("/feedback/update/{id}")
    public ResponseEntity<Object> updateFeedBack(@PathVariable String id, @RequestBody FeedBack feedBack) {
        return feedbackService.updateFeedBack(id, feedBack);
    }

    @DeleteMapping("/feedback/delete/{id}")
    public ResponseEntity<Object> deleteFeedBack(@PathVariable String id) {
        return feedbackService.deleteFeedBack(id);
    }


}
