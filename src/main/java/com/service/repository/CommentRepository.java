/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.service.repository;

import com.service.model.Comment;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author admin
 */
@Repository
public interface CommentRepository extends MongoRepository<Comment, String> {

    // Lấy tất cả các bình luận theo blogId
    List<Comment> findByBlogId(String blogId);
}
