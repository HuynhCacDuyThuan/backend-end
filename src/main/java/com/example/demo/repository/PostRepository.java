
package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.modal.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    // You can add custom queries here if needed in the future
	
}
