package com.landvibe.landlog.service;

import com.landvibe.landlog.controller.form.BlogForm;
import com.landvibe.landlog.domain.Blog;
import com.landvibe.landlog.repository.MemoryBlogRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    BlogForm invalidBlogForm = new BlogForm(null, null);

    BlogForm validBlogUpdateForm = new BlogForm("updated Title", "updated Contents");
    BlogForm invalidBlogUpdateForm = new BlogForm(null, null);


    @Test
    void validCreatorId_validBlogCreateForm_register() {
        // given
        Long expectedBlogId = 1L;

        Blog blog = new Blog();
        blog.setId(expectedBlogId);
        when(blogRepository.register(any(Blog.class))).thenReturn(blog);

        // when
        Long blogId = blogService.register(validCreatorId, validBlogForm);

        // then
        verify(blogRepository, times(1)).register(any(Blog.class));
        assertEquals(expectedBlogId, blogId);
    }

    @Test
    void invalidCreatorId_validBlogCreateForm_register() {
        assertThrows(IllegalArgumentException.class, () -> {
            blogService.register(invalidCreatorId, validBlogForm);
        });

        verify(blogRepository, never()).register(any(Blog.class));
    }

    @Test
    void validCreatorId_invalidBlogCreateForm_register() {
        assertThrows(IllegalArgumentException.class, () -> {
            blogService.register(validCreatorId, invalidBlogForm);
        });

        verify(blogRepository, never()).register(any(Blog.class));
    }

    @Test
    void invalidCreatorId_invalidBlogCreateForm_register() {
        assertThrows(IllegalArgumentException.class, () -> {
            blogService.register(invalidCreatorId, invalidBlogForm);
        });

        verify(blogRepository, never()).register(any(Blog.class));
    }

    @Test
    void valid_update() {
        String updatedTitle = "updated Title";
        String updatedContents = "updated Contents";

        Blog existingBlog = new Blog(validBlogId, validCreatorId, "title", "contents");
        when(blogRepository.findBlogByCreatorIdAndBlogId(validCreatorId, validBlogId)).thenReturn(existingBlog);

        blogService.update(validCreatorId, validBlogId, validBlogUpdateForm);

        verify(blogRepository, times(1)).update(validCreatorId, existingBlog);

        assertEquals(updatedTitle, existingBlog.getTitle());
        assertEquals(updatedContents, existingBlog.getContents());
    }

    @Test
    void invalid_update() {

        Blog existingBlog = new Blog(validBlogId, validCreatorId, "title", "contents");
        when(blogRepository.findBlogByCreatorIdAndBlogId(validCreatorId, validBlogId)).thenReturn(existingBlog);

        assertThrows(IllegalArgumentException.class, () -> {
            blogService.update(validCreatorId, validBlogId, invalidBlogUpdateForm);
        });

        verify(blogRepository, never()).update(validCreatorId, existingBlog);

    }

    @Test
    void delete() {
        
        Blog existingBlog = new Blog(validBlogId, validCreatorId, "Title", "Contents");

        when(blogRepository.findBlogByCreatorIdAndBlogId(validCreatorId, validBlogId)).thenReturn(existingBlog);

        blogService.delete(validCreatorId, validBlogId);

        verify(blogRepository, times(1)).findBlogByCreatorIdAndBlogId(validCreatorId, validBlogId);
        verify(blogRepository, times(1)).delete(validBlogId);

        when(blogRepository.findBlogByCreatorIdAndBlogId(validCreatorId, validBlogId)).thenReturn(null);

        existingBlog = blogRepository.findBlogByCreatorIdAndBlogId(validCreatorId, validCreatorId);
        assertNull(existingBlog);
    }

}