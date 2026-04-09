package com.parth.secureblog.service;

import com.parth.secureblog.exception.UserNotFoundException;
import com.parth.secureblog.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void testGetUserByIdNotFoundMessage() {
        Long testId = 123L;
        when(userRepository.findById(testId)).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            userService.getUserById(testId);
        });

        // This is expected to fail because it currently returns a metamodel string instead of "123"
        assertEquals("User not found with id: 123", exception.getMessage());
    }
}
