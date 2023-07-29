package com.landvibe.landlog.controller;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.landvibe.landlog.controller.form.BlogForm;
import com.landvibe.landlog.domain.Blog;
import com.landvibe.landlog.service.BlogService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/api/blogs")
public class BlogApiController {

    BlogService blogService;

    public BlogApiController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping(value = "")
    public List<Blog> list(@RequestParam long creatorId) {
        List<Blog> blogList = blogService.findAllBlogsByCreatorId(creatorId);
        return blogList;
    }

    @PostMapping(value = "")
    @JsonProperty("form")
    public Blog create(@RequestParam long creatorId, @RequestBody BlogForm form) {
        Blog createBlog = blogService.register(creatorId, form);
        return createBlog;
    }

    @GetMapping(value = "/{blogId}")
    @JsonProperty("blog")
    public Blog update(@RequestParam long creatorId, @PathVariable("blogId") long blogId) {
        Blog updateBlog = blogService.findBlogByCreatorIdAndBlogId(creatorId, blogId);
        return updateBlog;
    }

    @PutMapping(value = "/{blogId}")
    @JsonProperty("blog")
    public Blog update(@RequestParam long creatorId, @PathVariable("blogId") long blogId, @RequestBody Blog blog) {
        BlogForm form = new BlogForm(blog.getTitle(), blog.getContents());
        blogService.update(creatorId, blogId, form);
        return blog;
    }

    @DeleteMapping(value = "{blogId}")
    public void delete(@RequestParam long creatorId, @PathVariable("blogId") long blogId) {
        blogService.delete(creatorId, blogId);
    }
}