/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.service.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 *
 * @author admin
 */
@Document(collection = "blogs")
public class Blog implements Serializable {

    @Id
    private String blogId;

    private String blogSubject;
    private String blogContent;
    private String blogType;
    
    private Date blogCreateDate;
    private Date blogUpdateDate;
    private int blogEmotionsNumber;

    private String blogUserId;
    
    
    @Indexed
    private Long eventId;
    
    private List<String> eventListImgURL;

    public Blog() {
    }

    public String getBlogId() {
        return blogId;
    }

    public void setBlogId(String blogId) {
        this.blogId = blogId;
    }

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

    public Date getBlogCreateDate() {
        return blogCreateDate;
    }

    public void setBlogCreateDate(Date blogCreateDate) {
        this.blogCreateDate = blogCreateDate;
    }

    public Date getBlogUpdateDate() {
        return blogUpdateDate;
    }

    public void setBlogUpdateDate(Date blogUpdateDate) {
        this.blogUpdateDate = blogUpdateDate;
    }

    public String getBlogUserId() {
        return blogUserId;
    }

    public void setBlogUserId(String blogUserId) {
        this.blogUserId = blogUserId;
    }

    public int getBlogEmotionsNumber() {
        return blogEmotionsNumber;
    }

    public void setBlogEmotionsNumber(int blogEmotionsNumber) {
        this.blogEmotionsNumber = blogEmotionsNumber;
    }

    public List<String> getEventListImgURL() {
        return eventListImgURL;
    }

    public void setEventListImgURL(List<String> eventListImgURL) {
        this.eventListImgURL = eventListImgURL;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

}
