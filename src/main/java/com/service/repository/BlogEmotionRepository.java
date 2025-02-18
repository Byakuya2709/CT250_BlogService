/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.service.repository;

import com.service.model.Blog;
import com.service.model.BlogEmotion;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author admin
 */

@Repository
public interface BlogEmotionRepository extends MongoRepository<BlogEmotion, String> {
 Optional<BlogEmotion> findByBlogIdAndUserId(String blogId, String userId);
}
