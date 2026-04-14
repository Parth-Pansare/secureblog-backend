package com.parth.secureblog.dto;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class PostDTO {
    private Long id;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Content is required")
    private String content;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    private String authorName;
    private String authorEmail;

    public PostDTO() {}

    public PostDTO(Long id, String title, String content, LocalDateTime createdAt, LocalDateTime updatedAt, String authorName, String authorEmail) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.authorName = authorName;
        this.authorEmail = authorEmail;
    }

    public static PostDTOBuilder builder() {
        return new PostDTOBuilder();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public String getAuthorName() { return authorName; }
    public void setAuthorName(String authorName) { this.authorName = authorName; }

    public String getAuthorEmail() { return authorEmail; }
    public void setAuthorEmail(String authorEmail) { this.authorEmail = authorEmail; }

    public static class PostDTOBuilder {
        private Long id;
        private String title;
        private String content;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private String authorName;
        private String authorEmail;

        public PostDTOBuilder id(Long id) { this.id = id; return this; }
        public PostDTOBuilder title(String title) { this.title = title; return this; }
        public PostDTOBuilder content(String content) { this.content = content; return this; }
        public PostDTOBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public PostDTOBuilder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }
        public PostDTOBuilder authorName(String authorName) { this.authorName = authorName; return this; }
        public PostDTOBuilder authorEmail(String authorEmail) { this.authorEmail = authorEmail; return this; }

        public PostDTO build() {
            return new PostDTO(id, title, content, createdAt, updatedAt, authorName, authorEmail);
        }
    }
}
