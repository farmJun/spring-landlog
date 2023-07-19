package com.landvibe.landlog.repository;

import com.landvibe.landlog.domain.Blog;

import java.util.HashMap;
import java.util.Map;

public class MemoryBlogRepository implements BlogRepository {

    private static Map<Long, Blog> store = new HashMap<>();
    private static long sequence = 0L;


}
