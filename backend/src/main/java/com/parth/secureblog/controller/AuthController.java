package com.parth.secureblog.controller;

import com.parth.secureblog.config.JwtUtil;
import com.parth.secureblog.dto.AuthResponse;
import com.parth.secureblog.dto.LoginRequest;
import com.parth.secureblog.dto.RegisterRequest;
import com.parth.secureblog.dto.UserDTO;
import com.parth.secureblog.entity.User;
import com.parth.secureblog.repository.UserRepository;
import com.parth.secureblog.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public AuthController(UserService userService, BCryptPasswordEncoder passwordEncoder, JwtUtil jwtUtil, UserRepository userRepository) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public UserDTO register(@Valid @RequestBody RegisterRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        return userService.createUser(user);
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        User user = userRepository.findFirstByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());
        UserDTO userDTO = userService.mapToDTO(user);
        
        return AuthResponse.builder()
                .token(token)
                .user(userDTO)
                .type("Bearer")
                .build();
    }
}
