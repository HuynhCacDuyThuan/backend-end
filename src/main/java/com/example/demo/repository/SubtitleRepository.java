package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.modal.Subtitle;

@Repository
public interface SubtitleRepository extends JpaRepository<Subtitle, Long> {
    // You can add custom query methods here if needed
}
