package com.example.demo.service;



import com.example.demo.modal.Post;
import com.example.demo.repository.PostRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public Post savePost(Post post) {
        return postRepository.save(post);
    }
    public Optional<Post> getPostById(Long id) {
        return postRepository.findById(id);
    }
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

}

