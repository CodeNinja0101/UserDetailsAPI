package com.example.UserDetailsAPI.service;

import com.example.UserDetailsAPI.model.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {
    User createUser(User user);
    Optional<User> getUserById(int id);
    List<User> getAllUsers();
    Optional<User> updateUserDetails(int id, User user);
    Optional<User> deleteUser(int id);
    Optional<User> patchUserDetails(int id, Map<String, Object> patch);
}
