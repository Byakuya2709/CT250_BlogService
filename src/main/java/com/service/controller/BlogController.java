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
import com.service.request.EditBlog;
import com.service.request.UpdateComment;
import com.service.service.BlogService;
import com.service.service.FeedbackService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author admin
 */
@RestController
@RequestMapping("/blogs")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private FeedbackService feedbackService;

    @GetMapping("/filter")
    public ResponseEntity<?> getBlog(
            @RequestParam(required = false) Long eventId,
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer year,
            @RequestParam(defaultValue = "0") int page, // Đổi mặc định về 0 để tránh lỗi
            @RequestParam(defaultValue = "10") int size
    ) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Blog> blogs = blogService.getBlogs(eventId, userId, month, year, pageable);
            return ResponseHandler.resBuilder("Lấy danh sách blog thành công", HttpStatus.OK, blogs);
        } catch (Exception e) {
            return ResponseHandler.resBuilder("Có lỗi xảy ra khi lấy bài viết: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @GetMapping("/recent")
    public List<Blog> getRecentBlogs() {
        return blogService.getRecentBlogs();
    }

    @GetMapping("{blogId}")
    public ResponseEntity<?> getBlog(@PathVariable String blogId
    ) {
        try {
            Blog blog = blogService.getBlogById(blogId);
            return ResponseHandler.resBuilder("Lấy thông bài viết thành công.", HttpStatus.OK, blog);
        } catch (Exception e) {
            return ResponseHandler.resBuilder("Có lỗi xảy ra lấy tạo bài viết." + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @GetMapping("/{blogId}/comment")
    public ResponseEntity<?> getAllCommenOftBlog(@PathVariable String blogId,
            @RequestParam(defaultValue = "0") int page, // Đổi mặc định về 0 để tránh lỗi
            @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "cmtCreateDate"));
            Page<Comment> listCmt = blogService.getAllCommentOfBlog(blogId, pageable);
            return ResponseHandler.resBuilder("Lấy thông bài viết thành công.", HttpStatus.OK, listCmt);
        } catch (Exception e) {
            return ResponseHandler.resBuilder("Có lỗi xảy ra lấy tạo bài viết." + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @PostMapping("")
    public ResponseEntity<?> uploadBlog(@RequestBody BlogDTO blogDTO) {
        try {
            Blog blog = blogService.uploadBlog(blogDTO);
            return ResponseHandler.resBuilder("Tạo bài viết thành công.", HttpStatus.CREATED, blog);
        } catch (Exception e) {
            return ResponseHandler.resBuilder("Có lỗi xảy ra khi tạo bài viết." + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @PostMapping("/{blogId}/comment")
    public ResponseEntity<?> uploadBlogComment(@RequestBody CommentDTO commentDTO, @PathVariable String blogId) {
        try {
            Comment cmt = blogService.postComment(commentDTO, blogId);
            return ResponseHandler.resBuilder("Tạo bình luận bài viết thành công.", HttpStatus.CREATED, cmt);
        } catch (Exception e) {
            return ResponseHandler.resBuilder("Có lỗi xảy ra khi tạo bài viết." + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @PatchMapping("/comment/{commentId}")
    public ResponseEntity<?> editComment(@PathVariable String commentId, @RequestBody UpdateComment req) {
        try {
            Comment cmt = blogService.updateComment(commentId, req);
            return ResponseHandler.resBuilder("Cập nhật bình luận thành công", HttpStatus.OK, cmt);
        } catch (Exception e) {
            return ResponseHandler.resBuilder(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @DeleteMapping("/{blogId}")
    public ResponseEntity<?> deleteBlogAndComment(@PathVariable String blogId) {
        try {
            blogService.deleteBlogAndCommentRelated(blogId);
            return ResponseHandler.resBuilder("Xóa bài viết và các bài đăng thành công", HttpStatus.CREATED, null);
        } catch (Exception e) {
            return ResponseHandler.resBuilder(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @PatchMapping("/{blogId}")
    public ResponseEntity<?> editBlog(@PathVariable String blogId, @RequestBody EditBlog req) {
        try {
            Blog blog = blogService.updateCurrentBlog(blogId, req);
            return ResponseHandler.resBuilder("Cập nhật bài đăng thành công", HttpStatus.OK, blog);
        } catch (Exception e) {
            return ResponseHandler.resBuilder(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @PostMapping("/{blogId}/emotion")
    public ResponseEntity<Blog> toggleEmotion(@PathVariable String blogId, @RequestParam String userId) {
        blogService.toggleEmotion(blogId, userId);
        Blog updatedBlog = blogService.getBlogById(blogId);
        return ResponseEntity.ok(updatedBlog);
    }

    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<?> DeleteComment(@PathVariable String commentId) {
        try {
            blogService.deleteComment(commentId);
            return ResponseHandler.resBuilder("Xóa bình luận bài viết thành công.", HttpStatus.OK, null);
        } catch (Exception e) {
            return ResponseHandler.resBuilder(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    //thuy
    @PostMapping("/feedback")
    public ResponseEntity<Object> createFeedBack(@RequestBody FeedBack feedBack) {
        return feedbackService.createFeedBack(feedBack);
    }

    @GetMapping("/feedback")
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
