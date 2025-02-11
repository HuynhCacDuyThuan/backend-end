
package com.example.demo.repository;

import com.example.demo.modal.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    // You can add custom queries here if needed in the future
	
}
