package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.modal.User;


public interface UserRepository extends JpaRepository<User, Long> {
    // Bạn có thể tìm kiếm người dùng theo email hoặc id
    User findByEmail(String email);
}

