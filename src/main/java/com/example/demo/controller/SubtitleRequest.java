package com.example.demo.controller;

import org.springframework.web.multipart.MultipartFile;
public class SubtitleRequest {
    private Long id; // Add this field
    private String subtitle;
    private String image; // Base64 string

    // Constructor
    public SubtitleRequest() {
    }

    // Getters and Setters
    public Long getId() { // ✅ Add this method
        return id;
    }

    public void setId(Long id) { // ✅ Add this method
        this.id = id;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

	@Override
	public String toString() {
		return "SubtitleRequest [id=" + id + ", subtitle=" + subtitle + ", image=" + image + "]";
	}
    
}

