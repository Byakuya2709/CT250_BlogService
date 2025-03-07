package com.service.response;

import com.service.model.Blog;

import java.util.Date;
import java.util.List;

public class OneBlogResponse {
    private String blogId;
    private String blogSubject;
    private String blogContent;
    private String blogType;

    private Date blogCreateDate;
    private Date blogUpdateDate;
    private int blogEmotionsNumber;

    private String userId; //của công ty đăng blog

    private Long eventId;
    private List<String> eventListImgURL;

    // Method to convert Blog to OneBlogResponse
    public static OneBlogResponse toBlogResponse(Blog blog) {
        OneBlogResponse res = new OneBlogResponse();
        res.setBlogId(blog.getBlogId());
        res.setBlogSubject(blog.getBlogSubject());
        res.setBlogContent(blog.getBlogContent());
        res.setBlogType(blog.getBlogType());
        res.setBlogCreateDate(blog.getBlogCreateDate());
        res.setBlogUpdateDate(blog.getBlogUpdateDate());
        res.setBlogEmotionsNumber(blog.getBlogEmotionsNumber());
        res.setUserId(blog.getUserId());
        res.setEventId(blog.getEventId());
        res.setEventListImgURL(blog.getEventListImgURL());
        return res;
    }

    public OneBlogResponse() {
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

    public int getBlogEmotionsNumber() {
        return blogEmotionsNumber;
    }

    public void setBlogEmotionsNumber(int blogEmotionsNumber) {
        this.blogEmotionsNumber = blogEmotionsNumber;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public void setEventListImgURL(List<String> eventListImgURL) {
        this.eventListImgURL = eventListImgURL;
    }
}
