/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.service.request;

import java.util.List;

/**
 *
 * @author admin
 */
public class BlogDTO {
    private String blogSubject;
    private String blogContent;
    private String blogType;
    
    private int blogEmotionsNumber;
    private Long eventId;
    private String blogUserId;
    private List<String> eventListImgURL;

    // Getter và Setter
    public String getBlogSubject() {
        return blogSubject;
    }

    public void setBlogSubject(String blogSubject) {
        this.blogSubject = blogSubject;
    }

    public String getBlogContent() {
        return blogContent;
    }

    public void setBlogContent(String blogContent) {
        this.blogContent = blogContent;
    }

    public String getBlogType() {
        return blogType;
    }

    public void setBlogType(String blogType) {
        this.blogType = blogType;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

  

    public List<String> getEventListImgURL() {
        return eventListImgURL;
    }

    public int getBlogEmotionsNumber() {
        return blogEmotionsNumber;
    }

    public void setBlogEmotionsNumber(int blogEmotionsNumber) {
        this.blogEmotionsNumber = blogEmotionsNumber;
    }

    public String getBlogUserId() {
        return blogUserId;
    }

    public void setBlogUserId(String blogUserId) {
        this.blogUserId = blogUserId;
    }

    public void setEventListImgURL(List<String> eventListImgURL) {
        this.eventListImgURL = eventListImgURL;
    }
}
