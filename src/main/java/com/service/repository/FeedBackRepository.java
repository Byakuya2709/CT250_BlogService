/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.service.repository;

import com.service.model.FeedBack;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author admin
 */
@Repository
public interface FeedBackRepository extends MongoRepository<FeedBack, String> {

    Page<FeedBack> findByEventId(Long eventId, Pageable pageable);

    Page<FeedBack> findByFbUserId(String fbUserId, Pageable pageable);

    FeedBack findByTicketId(Long ticketId);

    boolean existsByTicketId(Long ticketId);
}
