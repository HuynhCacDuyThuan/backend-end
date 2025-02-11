package com.example.demo.modal;

import jakarta.persistence.*;

@Entity
public class Subtitle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String subtitle;

    private String imageUrl;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

	@Override
	public String toString() {
		return "Subtitle [id=" + id + ", subtitle=" + subtitle + ", imageUrl=" + imageUrl + "]";
	}
    
}

