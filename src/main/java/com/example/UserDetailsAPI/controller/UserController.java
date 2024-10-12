package com.example.UserDetailsAPI.controller;

import com.example.UserDetailsAPI.model.User;
import com.example.UserDetailsAPI.service.UserService;
import com.example.UserDetailsAPI.service.UserServiceImpl;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @PostMapping("create/user")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        logger.info("Creating new user: {}", user);
        User newUser = userService.createUser(user);
        logger.info("User created successfully with ID: {}", newUser.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Object>> getUserById(@PathVariable int id) {
        logger.info("Fetching user by ID: {}", id);
        Optional<User> existingUser = userService.getUserById(id);
        if (existingUser.isPresent()) {
            logger.info("User found: {}", existingUser.get());
            return ResponseEntity.ok(Optional.of(existingUser));
        } else {
            logger.warn("User not found with ID: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Optional.of("User not found with Id: " + id));
        }
    }

    @GetMapping()
    public List<User> getAllUsers() {
        logger.info("Getting all residents");
        return userService.getAllUsers();
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateUserDetails(@PathVariable int id, @Valid @RequestBody User user) {
        Optional<User> updatedUser = userService.updateUserDetails(id, user);
        if (updatedUser.isPresent()) {
            return ResponseEntity.ok(updatedUser.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with Id: " + id);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> patchUserDetails(@PathVariable int id, @RequestBody Map<String, Object> patch) {
        Optional<User> updatedUser = userService.patchUserDetails(id, patch);
        if (updatedUser.isPresent()) {
            return ResponseEntity.ok(updatedUser.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with Id: " + id);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        Optional<User> deletedUser = userService.deleteUser(id);
        if (deletedUser.isPresent()) {
            return ResponseEntity.ok("User deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found with Id: " + id);
        }
    }

}
