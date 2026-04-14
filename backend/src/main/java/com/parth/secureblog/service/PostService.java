package com.parth.secureblog.service;

import com.parth.secureblog.dto.PostDTO;
import com.parth.secureblog.entity.Post;
import com.parth.secureblog.entity.User;
import com.parth.secureblog.repository.PostRepository;
import com.parth.secureblog.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {
    private static final Logger log = LoggerFactory.getLogger(PostService.class);
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public PostDTO createPost(PostDTO postDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication == null) {
            log.error("Authentication is NULL in SecurityContext");
            throw new RuntimeException("Authentication failed: No security context found");
        }

        String email = authentication.getName();
        log.info("Authenticated user email: {}", email);

        if (email == null || email.equals("anonymousUser")) {
            log.error("User is anonymous or email is null");
            throw new RuntimeException("User not authenticated properly (anonymousUser)");
        }

        User user = userRepository.findFirstByEmail(email)
                .orElseThrow(() -> {
                    log.error("User with email {} not found in database", email);
                    return new RuntimeException("User not found in database: " + email);
                });

        log.info("Creating post for user: {} (ID: {})", user.getEmail(), user.getId());

        Post post = Post.builder()
                .title(postDTO.getTitle())
                .content(postDTO.getContent())
                .user(user)
                .build();

        Post savedPost = postRepository.save(post);
        log.info("Post created successfully with ID: {}", savedPost.getId());
        
        return mapToDTO(savedPost);
    }

    public Page<PostDTO> getAllPosts(Pageable pageable) {
        log.info("Fetching all posts with pagination: {}", pageable);
        return postRepository.findAll(pageable)
                .map(this::mapToDTO);
    }

    public PostDTO getPostById(Long id) {
        log.info("Fetching post by id: {}", id);
        Post post = postRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Post not found with id: {}", id);
                    return new RuntimeException("Post not found with id: " + id);
                });
        return mapToDTO(post);
    }

    public PostDTO updatePost(Long id, PostDTO postDTO) {
        log.info("Updating post with id: {}", id);
        Post post = postRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Post not found with id: {}", id);
                    return new RuntimeException("Post not found with id: " + id);
                });

        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        
        Post updatedPost = postRepository.save(post);
        log.info("Post updated successfully");
        return mapToDTO(updatedPost);
    }

    public void deletePost(Long id) {
        log.info("Deleting post with id: {}", id);
        if (!postRepository.existsById(id)) {
            log.error("Post not found with id: {}", id);
            throw new RuntimeException("Post not found with id: " + id);
        }
        postRepository.deleteById(id);
        log.info("Post deleted successfully");
    }

    private PostDTO mapToDTO(Post post) {
        return PostDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .authorName(post.getUser().getName())
                .authorEmail(post.getUser().getEmail())
                .build();
    }
}
