/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.service.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 *
 * @author admin
 */
@Document(collection = "comments")
public class Comment implements Serializable {

    @Id
    private String cmtId;  // MongoDB uses String by default for ID.
    private String cmtContent;
    private Date cmtCreateDate;
    private int cmtEmotionsNumber;
  
    private String cmtUserId;

    @Indexed
    private String blogId;

    // Getters and Setters

    public Comment() {
    }

    public String getCmtId() {
        return cmtId;
    }

    public void setCmtId(String cmtId) {
        this.cmtId = cmtId;
    }

    public String getCmtContent() {
        return cmtContent;
    }

    public void setCmtContent(String cmtContent) {
        this.cmtContent = cmtContent;
    }

    public Date getCmtCreateDate() {
        return cmtCreateDate;
    }

    public void setCmtCreateDate(Date cmtCreateDate) {
        this.cmtCreateDate = cmtCreateDate;
    }

    public int getCmtEmotionsNumber() {
        return cmtEmotionsNumber;
    }

    public void setCmtEmotionsNumber(int cmtEmotionsNumber) {
        this.cmtEmotionsNumber = cmtEmotionsNumber;
    }

    public String getCmtUserId() {
        return cmtUserId;
    }

    public void setCmtUserId(String cmtUserId) {
        this.cmtUserId = cmtUserId;
    }

    public String getBlogId() {
        return blogId;
    }

    public void setBlogId(String blogId) {
        this.blogId = blogId;
    }
}