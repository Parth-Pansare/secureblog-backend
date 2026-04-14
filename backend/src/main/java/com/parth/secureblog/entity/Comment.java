package com.parth.secureblog.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Content is required")
    private String content;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    public Comment() {}

    public Comment(Long id, String content, LocalDateTime createdAt, User user, Post post) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.user = user;
        this.post = post;
    }

    public static CommentBuilder builder() {
        return new CommentBuilder();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Post getPost() { return post; }
    public void setPost(Post post) { this.post = post; }

    public static class CommentBuilder {
        private Long id;
        private String content;
        private LocalDateTime createdAt;
        private User user;
        private Post post;

        public CommentBuilder id(Long id) { this.id = id; return this; }
        public CommentBuilder content(String content) { this.content = content; return this; }
        public CommentBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public CommentBuilder user(User user) { this.user = user; return this; }
        public CommentBuilder post(Post post) { this.post = post; return this; }

        public Comment build() {
            return new Comment(id, content, createdAt, user, post);
        }
    }
}
