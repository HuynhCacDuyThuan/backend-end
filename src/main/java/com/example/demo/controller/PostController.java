package com.example.demo.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.demo.modal.Post;
import com.example.demo.modal.Subtitle;
import com.example.demo.service.CloudinaryService;
import com.example.demo.service.PostService;
import com.example.demo.service.SubtitleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Base64;
@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = {"http://localhost:3000" , "http://14.225.29.33:3000" ,"http://zto.com.vn:3000","https://zto.com.vn","https://api.zto.com.vn"})

public class PostController {

    @Autowired
    private PostService postService;
  @Autowired
  SubtitleService subtitleService;
    @Autowired
    private CloudinaryService cloudinaryService;
   
    @PostMapping("/add")
    public ResponseEntity<Post> addPost(@RequestBody PostRequest postRequest) throws IOException {
        // Decode base64 main image and upload to Cloudinary (check if exists)
        String mainImageUrl = null;
        if (postRequest.getMainImage() != null && !postRequest.getMainImage().isEmpty()) {
            byte[] mainImageBytes = Base64.getDecoder().decode(postRequest.getMainImage().split(",")[1]); // Split off the "data:image/png;base64," part
            mainImageUrl = cloudinaryService.uploadFile(mainImageBytes); // Upload byte array to Cloudinary
        }

        // Create and save the post
        Post post = new Post();
        post.setTitle(postRequest.getTitle());
        post.setContent(postRequest.getContent());
        post.setMainImageUrl(mainImageUrl);

        // Handle subtitles (check if image exists before decoding)
        List<Subtitle> subtitles = postRequest.getSubtitles().stream().map(sub -> {
            Subtitle subtitle = new Subtitle();
            subtitle.setSubtitle(sub.getSubtitle());

            // Check if image exists and decode it
            if (sub.getImage() != null && !sub.getImage().isEmpty()) {
                byte[] subtitleImageBytes = Base64.getDecoder().decode(sub.getImage().split(",")[1]); // Same split for base64
                try {
                    subtitle.setImageUrl(cloudinaryService.uploadFile(subtitleImageBytes)); // Upload byte array to Cloudinary
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return subtitle;
        }).collect(Collectors.toList());

        post.setSubtitles(subtitles);
        Post savedPost = postService.savePost(post);

        return ResponseEntity.ok(savedPost);
    }


    // New API Endpoint for fetching a single post by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getPostById(@PathVariable Long id) {
        Optional<Post> postOptional = postService.getPostById(id);

        if (postOptional.isPresent()) {
            return ResponseEntity.ok(postOptional.get());
        } else {
            return ResponseEntity.status(404).body("Post not found with ID: " + id);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(@PathVariable Long id, @RequestBody PostRequest postRequest) {
        try {
            // Get the post to update
            Optional<Post> postOptional = postService.getPostById(id);
            if (postOptional.isEmpty()) {
                return ResponseEntity.status(404).body("Post not found with ID: " + id);
            }

            Post post = postOptional.get();
            System.out.println("Existing Post Data: " + post);

            post.setTitle(postRequest.getTitle());
            post.setContent(postRequest.getContent());

            // Handle main image (if updated or existing image)
            if (postRequest.getMainImage() != null && postRequest.getMainImage().contains(",")) {
                String[] base64Parts = postRequest.getMainImage().split(",", 2);
                if (base64Parts.length == 2) {
                    byte[] mainImageBytes = Base64.getDecoder().decode(base64Parts[1]);
                    String mainImageUrl = cloudinaryService.uploadFile(mainImageBytes);
                    post.setMainImageUrl(mainImageUrl);
                    System.out.println("Updated Main Image URL: " + mainImageUrl);
                }
            } else if (postRequest.getMainImage() == null || postRequest.getMainImage().trim().isEmpty()) {
                // If no new main image, keep the old one
                post.setMainImageUrl(post.getMainImageUrl());
                System.out.println("No new main image. Keeping the existing one: " + post.getMainImageUrl());
            }

            // Process subtitles (retain existing image if no new image is provided)
            List<Subtitle> updatedSubtitles = new ArrayList<>();
            System.out.println("postRequest.getSubtitles()" + postRequest.getSubtitles());
            for (SubtitleRequest sub : postRequest.getSubtitles()) {
            	 System.out.println("sub" + sub);
                Subtitle subtitle = null;

                if (sub.getId() != null) {
                    // Existing subtitle, find it by id
                    Optional<Subtitle> existingSubtitle = post.getSubtitles().stream()
                            .filter(s -> s.getId().equals(sub.getId()))
                            .findFirst();

                    if (existingSubtitle.isPresent()) {
                        subtitle = existingSubtitle.get();
                        subtitle.setSubtitle(sub.getSubtitle());

                        // Handle subtitle image if updated
                        if (sub.getImage() != null && sub.getImage().contains(",")) {
                            String[] subtitleBase64Parts = sub.getImage().split(",", 2);
                            if (subtitleBase64Parts.length == 2) {
                                try {
                                    byte[] subtitleImageBytes = Base64.getDecoder().decode(subtitleBase64Parts[1]);
                                    String subtitleImageUrl = cloudinaryService.uploadFile(subtitleImageBytes);
                                    subtitle.setImageUrl(subtitleImageUrl);
                                    System.out.println("Updated Subtitle Image URL: " + subtitleImageUrl);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else if (sub.getImage() == null || sub.getImage().trim().isEmpty()) {
                            // If no new image is provided, retain the existing image URL
                            subtitle.setImageUrl(subtitle.getImageUrl()); // Keep existing URL
                            System.out.println("No new subtitle image. Keeping the existing one: " + subtitle.getImageUrl());
                        }
                    } else {
                        // If the subtitle is not found, create a new one
                        subtitle = new Subtitle();
                        subtitle.setSubtitle(sub.getSubtitle());

                        // Handle subtitle image if new one provided
                        if (sub.getImage() != null && sub.getImage().contains(",")) {
                            String[] subtitleBase64Parts = sub.getImage().split(",", 2);
                            if (subtitleBase64Parts.length == 2) {
                                try {
                                    byte[] subtitleImageBytes = Base64.getDecoder().decode(subtitleBase64Parts[1]);
                                    String subtitleImageUrl = cloudinaryService.uploadFile(subtitleImageBytes);
                                    subtitle.setImageUrl(subtitleImageUrl);
                                    System.out.println("New Subtitle Image URL: " + subtitleImageUrl);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                } else {
                    // If it's a new subtitle without an ID
                    subtitle = new Subtitle();
                    subtitle.setSubtitle(sub.getSubtitle());

                    // Handle subtitle image if provided
                    if (sub.getImage() != null && sub.getImage().contains(",")) {
                        String[] subtitleBase64Parts = sub.getImage().split(",", 2);
                        if (subtitleBase64Parts.length == 2) {
                            try {
                                byte[] subtitleImageBytes = Base64.getDecoder().decode(subtitleBase64Parts[1]);
                                String subtitleImageUrl = cloudinaryService.uploadFile(subtitleImageBytes);
                                subtitle.setImageUrl(subtitleImageUrl);
                                System.out.println("New Subtitle Image URL: " + subtitleImageUrl);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

                updatedSubtitles.add(subtitle);
            }

            // Do not remove existing subtitles, just update the changed ones
            post.getSubtitles().clear();
            post.getSubtitles().addAll(updatedSubtitles);
            System.out.println("Updated Subtitles: " + updatedSubtitles);

            // Save the updated post
            Post updatedPost = postService.savePost(post);
            System.out.println("Updated Post Data: " + updatedPost);

            return ResponseEntity.ok(updatedPost);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error updating post: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
        try {
            // Get the post to delete
            Optional<Post> postOptional = postService.getPostById(id);
            if (postOptional.isEmpty()) {
                return ResponseEntity.status(404).body("Post not found with ID: " + id);
            }

            Post post = postOptional.get();

            // Delete associated subtitles
            List<Subtitle> subtitles = post.getSubtitles();
            if (subtitles != null && !subtitles.isEmpty()) {
                // If there are subtitles, delete them and associated images
                for (Subtitle subtitle : subtitles) {
                    // Delete the subtitle image from Cloudinary if it exists
                    if (subtitle.getImageUrl() != null) {
                        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                        		 "cloud_name", "dfy5bqyi7", 
                                 "api_key", "345177212793549", 
                                 "api_secret", "UkQd4fziqbih1h4v_fUTYfFTzQU"));

                        String imageId = extractImageIdFromUrl(subtitle.getImageUrl()); // Extract image ID from the URL
                        Map<String, String> result = cloudinary.uploader().destroy(imageId, ObjectUtils.emptyMap());
                        if ("ok".equals(result.get("result"))) {
                            System.out.println("Subtitle image deleted successfully from Cloudinary");
                        } else {
                            return ResponseEntity.status(500).body("Error deleting subtitle image from Cloudinary.");
                        }
                    }
                    // Delete the subtitle from the database
                    subtitleService.deleteSubtitle(subtitle.getId());
                }
            }

            // Delete the associated main image from Cloudinary
            if (post.getMainImageUrl() != null) {
                Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                		 "cloud_name", "dfy5bqyi7", 
                         "api_key", "345177212793549", 
                         "api_secret", "UkQd4fziqbih1h4v_fUTYfFTzQU"));

                String imageId = extractImageIdFromUrl(post.getMainImageUrl()); // Extract image ID from the URL
                Map<String, String> result = cloudinary.uploader().destroy(imageId, ObjectUtils.emptyMap());
                if ("ok".equals(result.get("result"))) {
                    System.out.println("Main image deleted successfully from Cloudinary");
                } else {
                    return ResponseEntity.status(500).body("Error deleting main image from Cloudinary.");
                }
            }

            // Delete the post from the database
            postService.deletePost(id);
            return ResponseEntity.ok("Post with ID " + id + " and its subtitles and images deleted successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error deleting post: " + e.getMessage());
        }
    }

    private String extractImageIdFromUrl(String imageUrl) {
        // Assuming image URL format is something like: https://res.cloudinary.com/your-cloud-name/image/upload/v1632947351/post-image.jpg
        // Extract the image ID from the URL (in this case, v1632947351/post-image.jpg)
        String[] parts = imageUrl.split("/");
        return parts[parts.length - 1].split("\\.")[0];  // Extracts the image ID from the URL
    }


//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> deletePost(@PathVariable Long id) {
//        try {
//            // Get the post to delete
//            Optional<Post> postOptional = postService.getPostById(id);
//            if (postOptional.isEmpty()) {
//                return ResponseEntity.status(404).body("Post not found with ID: " + id);
//            }
//
//            Post post = postOptional.get();
//
//            // Delete associated subtitles
//            List<Subtitle> subtitles = post.getSubtitles();
//            if (subtitles != null && !subtitles.isEmpty()) {
//                // If there are subtitles, delete them
//                for (Subtitle subtitle : subtitles) {
//                    // Assuming a method in the service to delete a subtitle
//                    subtitleService.deleteSubtitle(subtitle.getId());
//                }
//            }
//
//            // Delete the post
//            postService.deletePost(id);
//            return ResponseEntity.ok("Post with ID " + id + " and its subtitles deleted successfully.");
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(500).body("Error deleting post: " + e.getMessage());
//        }
//    }



    @GetMapping("/")
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }
}
