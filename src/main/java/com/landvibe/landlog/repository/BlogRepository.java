package com.landvibe.landlog.repository;

import com.landvibe.landlog.domain.Blog;
import java.util.List;

public interface BlogRepository {
    Blog register(Blog blog);

    List<Blog> findAllBlogsByCreatorId(Long creatorId);
}
