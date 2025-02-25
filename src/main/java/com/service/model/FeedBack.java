/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.service.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.io.Serializable;
import java.util.Date;

@Document(collection = "feedbacks")
public class FeedBack implements Serializable {

    @Id
    private String fbId;  
    
    private Long ticketId;   //mã vé đã mua của sự kiện
    private Long eventId;  //id sự kiện
    
    
    private String fbUserId; // nguoi đăng
    private String fbContent; // nội dung của feedback
    private Date fbCreateDate;
    private Long fbRate;  //đánh giá sao giá trị từ 1 đến 5
    // Getters and Setters

    public FeedBack() {
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    
    public String getFbId() {
        return fbId;
    }

    public void setFbId(String fbId) {
        this.fbId = fbId;
    }

    public String getFbContent() {
        return fbContent;
    }

    public void setFbContent(String fbContent) {
        this.fbContent = fbContent;
    }

    public Date getFbCreateDate() {
        return fbCreateDate;
    }

    public void setFbCreateDate(Date fbCreateDate) {
        this.fbCreateDate = fbCreateDate;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }
}