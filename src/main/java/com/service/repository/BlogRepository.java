/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.service.repository;

import com.service.model.Blog;
import java.util.Date;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author admin
 */
@Repository
public interface BlogRepository extends MongoRepository<Blog, String> {

    Optional<Blog> findById(String blogId);
// Tìm blog theo eventId với phân trang

    Page<Blog> findAll(Pageable pageable);

    @Query("{ '$expr': { '$and': [ { '$eq': [ { '$month': '$blogCreateDate' }, ?0 ] }, { '$eq': [ { '$year': '$blogCreateDate' }, ?1 ] } ] } }")
    Page<Blog> findByMonthAndYear(int month, int year, Pageable pageable);

    Page<Blog> findByEventId(Long eventId, Pageable pageable);

    Page<Blog> findByBlogUserId(String blogUserId, Pageable pageable);

    Page<Blog> findByEventIdAndBlogUserId(Long eventId, String blogUserId, Pageable pageable);

    @Query("{ 'eventId': ?0, '$expr': { '$and': [ { '$eq': [ { '$month': '$blogCreateDate' }, ?1 ] }, { '$eq': [ { '$year': '$blogCreateDate' }, ?2 ] } ] } }")
    Page<Blog> findByEventIdAndMonthAndYear(Long eventId, int month, int year, Pageable pageable);

    @Query("{ 'blogUserId': ?0, '$expr': { '$and': [ { '$eq': [ { '$month': '$blogCreateDate' }, ?1 ] }, { '$eq': [ { '$year': '$blogCreateDate' }, ?2 ] } ] } }")
    Page<Blog> findByBlogUserIdAndMonthAndYear(String blogUserId, int month, int year, Pageable pageable);

    @Query("{ 'eventId': ?0, 'blogUserId': ?1, '$expr': { '$and': [ { '$eq': [ { '$month': '$blogCreateDate' }, ?2 ] }, { '$eq': [ { '$year': '$blogCreateDate' }, ?3 ] } ] } }")
    Page<Blog> findByEventIdAndBlogUserIdAndMonthAndYear(Long eventId, String blogUserId, int month, int year, Pageable pageable);
}
