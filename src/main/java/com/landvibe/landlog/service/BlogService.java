package com.landvibe.landlog.service;

import com.landvibe.landlog.controller.form.BlogForm;
import com.landvibe.landlog.domain.Blog;
import com.landvibe.landlog.repository.BlogRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class BlogService {

    private final BlogRepository blogRepository;

    public BlogService(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    public Long register(Long creatorId, BlogForm form) {
        validateNullIds(creatorId);
        validateNullBlogForm(form);

        Blog blog = new Blog();
        blog.setCreatorId(creatorId);
        blog.setTitle(form.getTitle());
        blog.setContents(form.getContents());

        validateNullBlog(blog);

        blog = blogRepository.register(blog);

        validateNullBlog(blog);

        return blog.getId();
    }

    public void update(Long creatorId, Long blogId, BlogForm form) {
        validateNullIds(creatorId, blogId);

        Blog blog = blogRepository.findBlogByCreatorIdAndBlogId(creatorId, blogId);

        blog.setTitle(form.getTitle());
        blog.setContents(form.getContents());

        validateNullBlog(blog);
        blogRepository.update(creatorId, blog);
    }

    public void delete(Long creatorId, Long blogId) {
        validateNullIds(creatorId, blogId);

        Blog blog = blogRepository.findBlogByCreatorIdAndBlogId(creatorId, blogId);
        validateNullBlog(blog);

        blogRepository.delete(blogId);
    }

    public List<Blog> findAllBlogsByCreatorId(Long creatorId) {
        validateNullIds(creatorId);
        return blogRepository.findAllBlogsByCreatorId(creatorId);
    }

    public Blog findBlogByCreatorIdAndBlogId(Long creatorId, Long blogId) {
        validateNullIds(creatorId, blogId);
        Blog blog = blogRepository.findBlogByCreatorIdAndBlogId(creatorId, blogId);
        validateNullBlog(blog);
        return blog;
    }

    private void validateNullIds(Long... ids) {
        List<Long> idList = Arrays.stream(ids).toList();
        for (Long id : idList) {
            if (id == null) {
                throw new IllegalArgumentException("!!!null id!!!");
            }
        }
    }

    private void validateNullBlog(Blog blog) {
        if (blog == null || blog.getId() == null || blog.getCreatorId() == null || blog.getTitle() == null || blog.getContents() == null) {
            throw new IllegalArgumentException("Blog 객체와 모든 필드는 null이 아니어야 합니다.");
        }
    }

    private void validateNullBlogForm(BlogForm form) {
        if (form == null) {
            throw new IllegalArgumentException("!!!null form!!!");
        }
    }
}
