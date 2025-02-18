/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.service.service;

import com.service.model.Blog;
import com.service.repository.BlogRepository;
import com.service.request.BlogDTO;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author admin
 */
@Service
public class BlogService {

    @Autowired
    private BlogRepository blogRepository;

    public Blog uploadBlog(BlogDTO blogDTO) {
        // Tạo đối tượng Blog từ BlogDTO
        Blog blog = new Blog();

        blog.setBlogSubject(blogDTO.getBlogSubject());
        blog.setBlogContent(blogDTO.getBlogContent());
        blog.setBlogType(blogDTO.getBlogType());

        blog.setEventListImgURL(blogDTO.getEventListImgURL());
        blog.setBlogCreateDate(new Date());
        blog.setBlogUpdateDate(new Date());
        blog.setBlogEmotionsNumber(blogDTO.getBlogEmotionsNumber());

        blog.setBlogUserId(blogDTO.getBlogUserId()); // Giả sử userId đã có sẵn
        blog.setEventId(blogDTO.getEventId());
        // Lưu blog vào MongoDB
        return blogRepository.save(blog);
    }
}
