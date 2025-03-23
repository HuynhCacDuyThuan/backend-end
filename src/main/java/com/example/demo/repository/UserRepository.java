package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.modal.User;


public interface UserRepository extends JpaRepository<User, Long> {
	List<User> findAllByCustomerCode(String customerCode);
	Optional<User> findByCustomerCode(String customerCode);
    User findByEmail(String email);
    Page<User> findByEmailContainingIgnoreCaseOrNameContainingIgnoreCaseOrCustomerCodeContainingIgnoreCaseOrPhoneNumberContainingIgnoreCase(
            String email, String name, String customerCode, String phoneNumber, Pageable pageable);
}

