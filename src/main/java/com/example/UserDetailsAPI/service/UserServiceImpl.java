package com.example.UserDetailsAPI.service;

import com.example.UserDetailsAPI.exception.UserNotFoundException;
import com.example.UserDetailsAPI.model.User;
import com.example.UserDetailsAPI.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public User createUser(User user) {
        return userRepository.save(user);
    }

//    @Override
//    public Optional<User> getUserById(int id) {
//        return userRepository.findById((long) id);
//    }

    @Override
    public Optional<User> getUserById(int id) {
        return Optional.ofNullable(userRepository.findById((long) id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id)));
    }


    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    @Override
    @Transactional
    public Optional<User> updateUserDetails(int id, User user) {
        if (userRepository.existsById((long) id)) {
            user.setId(id);
            return Optional.of(userRepository.save(user));
        }
        return Optional.empty();
    }

//    @Override
//    @Transactional
//    public Optional<User> deleteUser(int id) {
//        Optional<User> existingUser = userRepository.findById((long) id);
//        if (existingUser.isPresent()) {
//            userRepository.deleteById((long) id);
//            return existingUser;
//        }
//        return Optional.empty();
//    }

    @Override
    public Optional<User> deleteUser(int id) {
        Optional<User> existingUser = userRepository.findById((long) id);
        if (existingUser.isPresent()) {
            userRepository.deleteById((long) id);
            return existingUser;
        }
        throw new UserNotFoundException("User not found with id: " + id);
    }

    @Override
    @Transactional
    public Optional<User> patchUserDetails(int id, Map<String, Object> patch) {
        return userRepository.findById((long) id).map(existingUser -> {
            patch.forEach((key, value) -> {
                switch (key) {
                    case "firstName":
                        existingUser.setFirstName((String) value);
                        break;
                    case "lastName":
                        existingUser.setLastName((String) value);
                        break;
                    case "phoneNumber":
                        existingUser.setPhoneNumber((String) value);
                        break;
                    case "emailId":
                        existingUser.setEmailId((String) value);
                        break;
                    case "address":
                        existingUser.setAddress((String) value);
                        break;
                }
            });
            return Optional.of(userRepository.save(existingUser));
        }).orElse(Optional.empty());
    }


}
