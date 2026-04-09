package com.parth.secureblog.service;


import com.parth.secureblog.entity.Role;
import com.parth.secureblog.entity.User;
import com.parth.secureblog.exception.UserNotFoundException;
import com.parth.secureblog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(User user) {

        if (userRepository.findFirstByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email is already registered!");
        }

        if (user.getPassword() == null) {
            throw new IllegalArgumentException("Password cannot be null");
        }

        // 🔐 Encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // 🧠 Set default role
        if (user.getRole() == null) {
            user.setRole(Role.USER);
        }

        return userRepository.save(user);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserById(Long id){
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    }
}
