/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.service.request;

/**
 *
 * @author admin
 */
public class EditBlog {
    String blogSubject;
    String blogContent;
    String blogType;

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

    public EditBlog() {
    }
    
}
