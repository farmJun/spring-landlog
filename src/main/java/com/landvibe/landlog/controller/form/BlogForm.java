package com.landvibe.landlog.controller.form;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BlogForm {
    private String title;
    private String contents;

    public BlogForm() {
    }

    public BlogForm(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }
}
