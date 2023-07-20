package com.landvibe.landlog.repository;

import com.landvibe.landlog.controller.form.BlogUpdateForm;
import com.landvibe.landlog.domain.Blog;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class MemoryBlogRepository implements BlogRepository {

    private static Map<Long, Blog> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Blog register(Blog blog) {
        blog.setId(++sequence);
        store.put(blog.getId(), blog);
        return blog;
    }

    @Override
    public Long update(Long blogId, Blog updatedBlog) {
        store.put(blogId, updatedBlog);
        return blogId;
    }

    @Override
    public List<Blog> findAllBlogsByCreatorId(Long creatorId) {
        return store.values()
                .stream()
                .filter(blog -> blog.getCreatorId().equals(creatorId))
                .collect(Collectors.toList());
    }

    @Override
    public Blog findBlogByCreatorIdAndBlogId(Long creatorId, Long blogId) {
        return store.values()
                .stream()
                .filter(blog -> blog.getCreatorId().equals(creatorId) && blog.getId().equals(blogId))
                .findAny()
                .orElse(null);
    }

}
