/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.service.request;

import java.util.Date;

/**
 *
 * @author admin
 */
public class CommentDTO {
    String cmtContent;
    int cmtEmotionsNumber;
    
    String cmtUserId;

    public String getCmtContent() {
        return cmtContent;
    }

    public void setCmtContent(String cmtContent) {
        this.cmtContent = cmtContent;
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

    
}
