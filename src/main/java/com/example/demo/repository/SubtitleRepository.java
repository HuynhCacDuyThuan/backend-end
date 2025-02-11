package com.example.demo.repository;

import com.example.demo.modal.Subtitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubtitleRepository extends JpaRepository<Subtitle, Long> {
    // You can add custom query methods here if needed
}
