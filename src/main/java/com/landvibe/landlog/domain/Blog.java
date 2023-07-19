package com.landvibe.landlog.domain;

public class Blog {
    private Long id;
    private Long creatorId;
    private String title;
    private String contents;

    public Blog() {
    }

    public Blog(Long id, Long creatorId, String title, String contents) {
        this.id = id;
        this.creatorId = creatorId;
        this.title = title;
        this.contents = contents;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}
