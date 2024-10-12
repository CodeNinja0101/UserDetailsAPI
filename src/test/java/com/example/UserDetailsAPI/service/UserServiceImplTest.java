package com.example.UserDetailsAPI.service;

import com.example.UserDetailsAPI.model.User;
import com.example.UserDetailsAPI.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void testCreateUser() {
        User user = new User();
        user.setFirstName("Jane");
        user.setLastName("Doe");
        user.setPhoneNumber("1234567890");
        user.setEmailId("jane.doe@example.com");
        user.setAddress("1234 Elm St");

        when(userRepository.save(any(User.class))).thenReturn(user);

        User createdUser = userService.createUser(user);
        assertNotNull(createdUser);
        assertEquals("Jane", createdUser.getFirstName());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testGetUserById_userExists() {
        User user = new User();
        user.setId(1);
        when(userRepository.findById((long) 1)).thenReturn(Optional.of(user));

        Optional<User> foundUser = userService.getUserById(1);
        assertTrue(foundUser.isPresent());
        assertEquals(1, foundUser.get().getId());
    }

    @Test
    void testGetUserById_userDoesNotExist() {
        when(userRepository.findById((long) 1)).thenReturn(Optional.empty());

        Optional<User> foundUser = userService.getUserById(1);
        assertFalse(foundUser.isPresent());
    }
}
