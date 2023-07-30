package com.landvibe.landlog.repository;

import com.landvibe.landlog.controller.form.BlogForm;
import com.landvibe.landlog.domain.Blog;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemoryBlogRepositoryTest {

    MemoryBlogRepository memoryBlogRepository = new MemoryBlogRepository();

    Long validBlogId = 1L;
    Long validCreatorId = 1L;

    @Test
    @DisplayName("블로그 등록 성공")
    void valid_register() {
        Blog blog = Blog.builder()
                .creatorId(validCreatorId)
                .title("title")
                .contents("contents")
                .build();
        memoryBlogRepository.register(blog);

        Blog findBlog = memoryBlogRepository.findBlogByCreatorIdAndBlogId(validCreatorId, blog.getId()).get();

        assertEquals(blog, findBlog);
    }

    @Test
    @DisplayName("블로그 업데이트 성공")
    void update() {
        String expectedTitle = "update title";
        String expectedContents = "update contents";

        Blog blog = Blog.builder()
                .creatorId(validCreatorId)
                .title("title")
                .contents("contents")
                .build();

        Blog updateBlog = Blog.builder()
                .creatorId(validCreatorId)
                .title("update title")
                .contents("update contents")
                .build();

        memoryBlogRepository.register(blog);
        updateBlog.setId(blog.getId());

        memoryBlogRepository.update(blog.getId(), updateBlog);

        Optional<Blog> findBlog = memoryBlogRepository.findBlogByCreatorIdAndBlogId(validCreatorId, validBlogId);

        String updateTitle = findBlog.get().getTitle();
        String updateContents = findBlog.get().getContents();

        assertEquals(expectedTitle, updateTitle);
        assertEquals(expectedContents, updateContents);
    }

    @Test
    @DisplayName("블로그 삭제 성공")
    void delete() {
        Blog blog = Blog.builder()
                .creatorId(validCreatorId)
                .title("title")
                .contents("contents")
                .build();
        memoryBlogRepository.register(blog);

        memoryBlogRepository.delete(blog.getId());

        Optional<Blog> findBlog = memoryBlogRepository.findBlogByCreatorIdAndBlogId(validCreatorId, blog.getId());

        assertThat(findBlog.isEmpty()).isEqualTo(true);
    }
}