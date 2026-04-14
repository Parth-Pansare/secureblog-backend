package com.parth.secureblog.service;

import com.parth.secureblog.dto.CommentDTO;
import com.parth.secureblog.entity.Comment;
import com.parth.secureblog.entity.Post;
import com.parth.secureblog.entity.User;
import com.parth.secureblog.exception.UserNotFoundException;
import com.parth.secureblog.repository.CommentRepository;
import com.parth.secureblog.repository.PostRepository;
import com.parth.secureblog.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private static final Logger log = LoggerFactory.getLogger(CommentService.class);
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    @Autowired
    public CommentService(CommentRepository commentRepository, PostRepository postRepository, 
                          UserRepository userRepository, UserService userService) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    public CommentDTO addComment(Long postId, CommentDTO commentDTO) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("Adding comment to post {} by user {}", postId, email);

        User user = userRepository.findFirstByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Authenticated user not found"));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + postId));

        Comment comment = Comment.builder()
                .content(commentDTO.getContent())
                .user(user)
                .post(post)
                .build();

        Comment savedComment = commentRepository.save(comment);
        log.info("Comment added with id: {}", savedComment.getId());
        return mapToDTO(savedComment);
    }

    public List<CommentDTO> getCommentsByPostId(Long postId) {
        log.info("Fetching comments for post id: {}", postId);
        return commentRepository.findByPostId(postId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public void deleteComment(Long id) {
        log.info("Deleting comment with id: {}", id);
        if (!commentRepository.existsById(id)) {
            log.error("Comment not found with id: {}", id);
            throw new RuntimeException("Comment not found with id: " + id);
        }
        commentRepository.deleteById(id);
        log.info("Comment deleted successfully");
    }

    private CommentDTO mapToDTO(Comment comment) {
        return CommentDTO.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .user(userService.mapToDTO(comment.getUser()))
                .postId(comment.getPost().getId())
                .build();
    }
}
