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
    private String fbId;  // MongoDB uses String for ID by default
    private String fbContent;
    private Date fbCreateDate;
    private int fbRate; //mới thêm

    private String ticketId;//mới thêm
    private Long eventId;

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

    public int getFbRate() {
        return fbRate;
    }

    public void setFbRate(int fbRate) {
        this.fbRate = fbRate;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }
}