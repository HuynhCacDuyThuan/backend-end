package com.example.demo.controller;

import com.example.demo.dto.BannerDTO;
import com.example.demo.modal.Banner;
import com.example.demo.repository.BannerRepository;
import com.example.demo.service.CloudinaryService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/banner")
@CrossOrigin(origins = "http://localhost:3000")
public class BannerController {

	@Autowired
	private CloudinaryService cloudinaryService;

	@Autowired
	private BannerRepository bannerRepository;

	@PostMapping("/add")
	public ResponseEntity<?> addBanner(@RequestParam("title") String title,
			@RequestParam("description") String description, @RequestParam("file") MultipartFile file) {
		try {
			// Upload ảnh lên Cloudinary
			String imageUrl = cloudinaryService.uploadFile(file);

			// Tạo đối tượng Banner và lưu vào CSDL
			Banner banner = new Banner();
			banner.setTitle(title);
			banner.setDescription(description);
			banner.setImageUrl(imageUrl);
			Banner savedBanner = bannerRepository.save(banner);

			// Tạo DTO trả về
			BannerDTO bannerDTO = new BannerDTO();
			bannerDTO.setTitle(savedBanner.getTitle());
			bannerDTO.setDescription(savedBanner.getDescription());
			bannerDTO.setImageUrl(savedBanner.getImageUrl());

			return ResponseEntity.ok(bannerDTO);
		} catch (Exception e) {
			return ResponseEntity.status(500).body("Error uploading file: " + e.getMessage());
		}
	}

	@GetMapping("/all")
	public ResponseEntity<List<Banner>> getAllBanners() {
		List<Banner> banners = bannerRepository.findAll();
		return ResponseEntity.ok(banners);
	}
}
