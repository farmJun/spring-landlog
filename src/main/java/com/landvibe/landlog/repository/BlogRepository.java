package com.landvibe.landlog.repository;

import com.landvibe.landlog.domain.Blog;

import java.util.List;
import java.util.Optional;

public interface BlogRepository {
    Blog register(Blog blog);

    Long update(Long blogId, Blog updatedBlog);

    Long delete(Long blogId);

    List<Blog> findAllBlogsByCreatorId(Long creatorId);

    Optional<Blog> findBlogByCreatorIdAndBlogId(Long creatorId, Long blogId);

}
