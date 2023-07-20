package com.landvibe.landlog.service;

import com.landvibe.landlog.controller.form.BlogCreateForm;
import com.landvibe.landlog.controller.form.BlogUpdateForm;
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

    public Long register(Long creatorId, BlogCreateForm form) {
        validateNullIds(creatorId);

        Blog blog = new Blog();
        blog.setCreatorId(creatorId);
        blog.setTitle(form.getTitle());
        blog.setContents(form.getContents());

        validateNullBlog(blog);

        blogRepository.register(blog);
        return blog.getId();
    }

    public void update(Long creatorId, Long blogId, BlogUpdateForm form) {
        validateNullIds(creatorId, blogId);

        Blog blog = blogRepository.findBlogByCreatorIdAndBlogId(creatorId, blogId);
        validateNullBlog(blog);

        blog.setTitle(form.getTitle());
        blog.setContents(form.getContents());

        blogRepository.update(creatorId, blog);
    }

    public void delete(Long creatorId, Long blogId){
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
        if (blog == null) {
            throw new IllegalArgumentException("일치하는 게시글이 없습니다!");
        }
    }
}
