package com.landvibe.landlog.service;

import com.landvibe.landlog.controller.form.BlogForm;
import com.landvibe.landlog.domain.Blog;
import com.landvibe.landlog.repository.BlogRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class BlogService {

    private final BlogRepository blogRepository;

    public BlogService(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    public Blog register(Long creatorId, BlogForm form) {
        validateNullIds(creatorId);
        validateNullBlogForm(form);

        Blog blog = Blog.builder()
                .creatorId(creatorId)
                .title(form.getTitle())
                .contents(form.getContents())
                .build();

        blog = blogRepository.register(blog);
        validateNullBlog(Optional.ofNullable(blog));

        return blog;
    }

    public void update(Long creatorId, Long blogId, BlogForm form) {
        validateNullIds(creatorId, blogId);
        validateNullBlogForm(form);

        Optional<Blog> findBlog = blogRepository.findBlogByCreatorIdAndBlogId(creatorId, blogId);
        validateNullBlog(findBlog);

        Blog blog = findBlog.get();
        blog.setTitle(form.getTitle());
        blog.setContents(form.getContents());

        blogRepository.update(creatorId, blog);
    }

    public void delete(Long creatorId, Long blogId) {
        validateNullIds(creatorId, blogId);

        Optional<Blog> findBlog = blogRepository.findBlogByCreatorIdAndBlogId(creatorId, blogId);
        validateNullBlog(findBlog);

        blogRepository.delete(blogId);
    }

    public List<Blog> findAllBlogsByCreatorId(Long creatorId) {
        validateNullIds(creatorId);
        return blogRepository.findAllBlogsByCreatorId(creatorId);
    }

    public Blog findBlogByCreatorIdAndBlogId(Long creatorId, Long blogId) {
        validateNullIds(creatorId, blogId);

        Optional<Blog> findBlog = blogRepository.findBlogByCreatorIdAndBlogId(creatorId, blogId);
        validateNullBlog(findBlog);
        return findBlog.get();
    }

    private void validateNullIds(Long... ids) {
        List<Long> idList = Arrays.stream(ids).toList();
        for (Long id : idList) {
            if (id == null) {
                throw new IllegalArgumentException("모든 id는 null이 아니어야 합니다.");
            }
        }
    }

    private void validateNullBlog(Optional<Blog> blog) {
        if (blog.isEmpty()) {
            throw new IllegalArgumentException("Blog 객체와 모든 필드는 null이 아니어야 합니다.");
        }
    }

    private void validateNullBlogForm(BlogForm form) {
        if (form == null || form.getTitle() == "" || form.getContents() == "") {
            throw new IllegalArgumentException("BlogForm 객체가 null이거나, 필드가 올바르지 않습니다.");
        }
    }
}
