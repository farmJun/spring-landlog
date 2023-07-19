package com.landvibe.landlog.service;

import com.landvibe.landlog.domain.Blog;
import com.landvibe.landlog.repository.BlogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogService {

    private final BlogRepository blogRepository;

    public BlogService(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    public Long register(Blog blog) {
        blogRepository.register(blog);
        return blog.getId();
    }

    public List<Blog> findAllBlogsByCreatorId(Long creatorId){
        return blogRepository.findAllBlogsByCreatorId(creatorId);
    }
}
