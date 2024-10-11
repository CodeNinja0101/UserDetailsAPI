package com.example.UserDetailsAPI.repository;

import com.example.UserDetailsAPI.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
