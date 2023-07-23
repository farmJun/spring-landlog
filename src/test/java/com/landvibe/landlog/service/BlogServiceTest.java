package com.landvibe.landlog.service;

import com.landvibe.landlog.controller.form.BlogForm;
import com.landvibe.landlog.domain.Blog;
import com.landvibe.landlog.domain.Member;
import com.landvibe.landlog.repository.MemoryBlogRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BlogServiceTest {


    @Mock
    MemoryBlogRepository blogRepository;
    @InjectMocks
    BlogService blogService;

    Long validBlogId = 1L;
    Long invalidBlogId = null;
    Long validCreatorId = 1L;
    Long invalidCreatorId = null;
    BlogForm validBlogForm = new BlogForm("title", "contents");
    BlogForm invalidBlogForm = new BlogForm("", "");

    BlogForm validBlogUpdateForm = new BlogForm("updated Title", "updated Contents");
    BlogForm invalidBlogUpdateForm = new BlogForm("", "");

    private Blog createBlog() {
        return new Blog(validBlogId, validCreatorId, "title", "contents");
    }

    private Member createMember(){
        return new Member(1L, "name","email","password");
    }


    @DisplayName("유효하지 않은 creator id, 유효한 blog form -> 블로그 생성 실패")
    @Test
    void invalidCreatorId_validBlogCreateForm_register() {
        assertThrows(IllegalArgumentException.class, () -> {
            blogService.register(invalidCreatorId, validBlogForm);
        });

        verify(blogRepository, never()).register(any(Blog.class));
    }

    @DisplayName("유효한 creator id, 유효하지 않은 blog form -> 블로그 생성 실패")
    @Test
    void validCreatorId_invalidBlogCreateForm_register() {
        assertThrows(IllegalArgumentException.class, () -> {
            blogService.register(validCreatorId, invalidBlogForm);
        });

        verify(blogRepository, never()).register(any(Blog.class));
    }

    @DisplayName("유효하지 않은 creator id, 유효하지 않은 blog form -> 블로그 생성 실패")
    @Test
    void invalidCreatorId_invalidBlogCreateForm_register() {
        assertThrows(IllegalArgumentException.class, () -> {
            blogService.register(invalidCreatorId, invalidBlogForm);
        });

        verify(blogRepository, never()).register(any(Blog.class));
    }

    @DisplayName("블로그 업데이트 성공")
    @Test
    void valid_update() {
        String updatedTitle = "updated Title";
        String updatedContents = "updated Contents";

        Blog existingBlog = new Blog(validBlogId, validCreatorId, "title", "contents");
        when(blogRepository.findBlogByCreatorIdAndBlogId(validCreatorId, validBlogId)).thenReturn(Optional.of(existingBlog));

        blogService.update(validCreatorId, validBlogId, validBlogUpdateForm);

        verify(blogRepository, times(1)).update(validCreatorId, existingBlog);

        assertEquals(updatedTitle, existingBlog.getTitle());
        assertEquals(updatedContents, existingBlog.getContents());
    }

    @DisplayName("블로그 업데이트 실패")
    @Test
    void invalid_update() {
        Blog existingBlog = createBlog();

        assertThrows(IllegalArgumentException.class, () -> blogService.update(validCreatorId, validBlogId, invalidBlogUpdateForm));

        verify(blogRepository, never()).update(validCreatorId, existingBlog);
    }

    @DisplayName("블로그 삭제 성공")
    @Test
    void valid_delete() {
        blogService.register(validCreatorId,validBlogForm);

        Optional<Blog> existingBlog = Optional.of(new Blog(validBlogId, validCreatorId, "title", "contents"));

        when(blogRepository.findBlogByCreatorIdAndBlogId(validCreatorId, validBlogId)).thenReturn(existingBlog);

        blogService.delete(validCreatorId, validBlogId);

        verify(blogRepository, times(1)).findBlogByCreatorIdAndBlogId(validCreatorId, validBlogId);
        verify(blogRepository, times(1)).delete(validBlogId);

        when(blogRepository.findBlogByCreatorIdAndBlogId(validCreatorId, validBlogId)).thenReturn(null);

        existingBlog = blogRepository.findBlogByCreatorIdAndBlogId(validCreatorId, validCreatorId);
        assertNull(existingBlog);
    }

    @DisplayName("블로그 삭제 실패")
    @Test
    void invalid_delete() {
        blogService.register(validCreatorId,validBlogForm);

        assertThrows(IllegalArgumentException.class, () -> {
            blogService.delete(invalidCreatorId, invalidBlogId);
        });

        verify(blogRepository, never()).findBlogByCreatorIdAndBlogId(validCreatorId, validBlogId);
        verify(blogRepository, never()).delete(validBlogId);
    }
}