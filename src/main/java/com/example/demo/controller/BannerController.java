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

	
	 @DeleteMapping("/delete/{id}")
	    public ResponseEntity<?> deleteBanner(@PathVariable Long id) {
	        try {
	            // Kiểm tra xem banner có tồn tại không
	            Banner banner = bannerRepository.findById(id).orElse(null);
	            if (banner == null) {
	                return ResponseEntity.status(404).body("Banner not found with id " + id);
	            }

	            // Xóa banner
	            bannerRepository.delete(banner);
	            return ResponseEntity.ok("Banner deleted successfully!");
	        } catch (Exception e) {
	            return ResponseEntity.status(500).body("Error deleting banner: " + e.getMessage());
	        }
	    }
	@GetMapping("/all")
	public ResponseEntity<List<Banner>> getAllBanners() {
		List<Banner> banners = bannerRepository.findAll();
		return ResponseEntity.ok(banners);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateBanner(@PathVariable Long id, 
	                                      @RequestParam("title") String title,
	                                      @RequestParam("description") String description,
	                                      @RequestParam(value = "file", required = false) MultipartFile file) {
	    try {
	        // Kiểm tra xem banner có tồn tại không
	        Banner banner = bannerRepository.findById(id).orElse(null);
	        if (banner == null) {
	            return ResponseEntity.status(404).body("Banner not found with id " + id);
	        }

	        // Cập nhật thông tin của banner
	        banner.setTitle(title);
	        banner.setDescription(description);

	        // Nếu có ảnh mới, upload ảnh và cập nhật URL
	        if (file != null && !file.isEmpty()) {
	            String imageUrl = cloudinaryService.uploadFile(file);
	            banner.setImageUrl(imageUrl);
	        }

	        // Lưu lại banner sau khi sửa
	        Banner updatedBanner = bannerRepository.save(banner);

	        // Tạo DTO trả về
	        BannerDTO bannerDTO = new BannerDTO();
	        bannerDTO.setTitle(updatedBanner.getTitle());
	        bannerDTO.setDescription(updatedBanner.getDescription());
	        bannerDTO.setImageUrl(updatedBanner.getImageUrl());

	        return ResponseEntity.ok(bannerDTO);

	    } catch (Exception e) {
	        return ResponseEntity.status(500).body("Error updating banner: " + e.getMessage());
	    }
	}

}
