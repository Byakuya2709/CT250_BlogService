/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.service.controller;

import com.service.exception.ResponseHandler;
import com.service.model.Blog;
import com.service.model.Comment;
import com.service.model.FeedBack;
import com.service.request.BlogDTO;
import com.service.request.CommentDTO;
import com.service.service.BlogService;
import com.service.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/get")
    public ResponseEntity<String> getBlog() {
        return ResponseEntity.ok("Blog data here");
    }
    
    @PostMapping("/upload")
    public ResponseEntity<?> uploadBlog(@RequestBody BlogDTO blogDTO) {
        try {
            Blog blog = blogService.uploadBlog(blogDTO);
            return ResponseHandler.resBuilder("Tạo bài viết thành công.", HttpStatus.CREATED, blog);
        } catch (Exception e) {
            return ResponseHandler.resBuilder("Có lỗi xảy ra khi tạo bài viết." + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @PostMapping("/{blogId}/post-comment")
    public ResponseEntity<?> uploadBlogComment(@RequestBody CommentDTO commentDTO, @PathVariable String blogId) {
        try {
            Comment cmt = blogService.postComment(commentDTO, blogId);
            return ResponseHandler.resBuilder("Tạo bình luận bài viết thành công.", HttpStatus.CREATED, cmt);
        } catch (Exception e) {
            return ResponseHandler.resBuilder("Có lỗi xảy ra khi tạo bài viết." + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }
    
    @PostMapping("/{blogId}/emotion")
    public ResponseEntity<Blog> toggleEmotion(@PathVariable String blogId, @RequestParam String userId) {
        blogService.toggleEmotion(blogId, userId);
        Blog updatedBlog = blogService.getBlogById(blogId);
        return ResponseEntity.ok(updatedBlog);
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
