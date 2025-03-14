package com.example.demo.service;

import com.example.demo.modal.User;
import com.example.demo.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User updateCustomerCode(Long userId, String customerCode) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
         
            return userRepository.save(user); // Lưu vào DB
        } else {
            throw new RuntimeException("Không tìm thấy người dùng với ID: " + userId);
        }
    }
}
