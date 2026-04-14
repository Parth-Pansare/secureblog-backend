package com.parth.secureblog.dto;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class CommentDTO {
    private Long id;

    @NotBlank(message = "Content is required")
    private String content;

    private LocalDateTime createdAt;
    private UserDTO user;
    private Long postId;

    public CommentDTO() {}

    public CommentDTO(Long id, String content, LocalDateTime createdAt, UserDTO user, Long postId) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.user = user;
        this.postId = postId;
    }

    public static CommentDTOBuilder builder() {
        return new CommentDTOBuilder();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public UserDTO getUser() { return user; }
    public void setUser(UserDTO user) { this.user = user; }

    public Long getPostId() { return postId; }
    public void setPostId(Long postId) { this.postId = postId; }

    public static class CommentDTOBuilder {
        private Long id;
        private String content;
        private LocalDateTime createdAt;
        private UserDTO user;
        private Long postId;

        public CommentDTOBuilder id(Long id) { this.id = id; return this; }
        public CommentDTOBuilder content(String content) { this.content = content; return this; }
        public CommentDTOBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public CommentDTOBuilder user(UserDTO user) { this.user = user; return this; }
        public CommentDTOBuilder postId(Long postId) { this.postId = postId; return this; }

        public CommentDTO build() {
            return new CommentDTO(id, content, createdAt, user, postId);
        }
    }
}
