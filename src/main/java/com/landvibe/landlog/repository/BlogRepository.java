package com.landvibe.landlog.repository;

import com.landvibe.landlog.domain.Blog;

import java.util.List;

public interface BlogRepository {
    Blog register(Blog blog);

    Long update(Long blogId, Blog updatedBlog);

    Long delete(Long blogId);

    List<Blog> findAllBlogsByCreatorId(Long creatorId);

    Blog findBlogByCreatorIdAndBlogId(Long creatorId, Long blogId);

}
