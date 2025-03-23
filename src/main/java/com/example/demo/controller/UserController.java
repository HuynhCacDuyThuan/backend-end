package com.example.demo.controller;

import com.example.demo.modal.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = { "http://localhost:3000" , "http://14.225.29.33:3000" ,"http://zto.com.vn:3000","https://zto.com.vn" ,"https://api.zto.com.vn"})
@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	UserService userService;
@GetMapping
	public Page<User> getAllUsers(
            @RequestParam(required = false) String search, // Từ khóa tìm kiếm (không bắt buộc)
            @RequestParam(defaultValue = "0") int page,   // Số trang, mặc định là 0
            @RequestParam(defaultValue = "10") int size   // Số bản ghi mỗi trang, mặc định là 10
    ) {
        // Tạo đối tượng Pageable để quản lý phân trang
        Pageable pageable = PageRequest.of(page, size);

        // Kiểm tra nếu có từ khóa tìm kiếm
        if (search != null && !search.isEmpty()) {
            return userRepository.findByEmailContainingIgnoreCaseOrNameContainingIgnoreCaseOrCustomerCodeContainingIgnoreCaseOrPhoneNumberContainingIgnoreCase(
                    search, search, search, search, pageable);
        } else {
            // Nếu không có tìm kiếm, trả về tất cả người dùng với phân trang
            return userRepository.findAll(pageable);
        }
    }

@GetMapping("/all")
public List<User> getAllUsers() {
	return userRepository.findAll(); // Trả về tất cả người dùng
}
@PutMapping("/{id}/update-code")
public ResponseEntity<?> updateCustomerCode(@PathVariable Long id, @RequestBody Map<String, String> payload) {
    try {
        
        Optional<User> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()) {
            return ResponseEntity.status(404).body("Không tìm thấy người dùng!");
        }

       
        String newCustomerCode = payload.get("customerCode");
        if (newCustomerCode == null || newCustomerCode.trim().isEmpty()) {
            return ResponseEntity.status(400).body("Mã khách hàng không được để trống!");
        }

        
        List<User> usersWithSameCode = userRepository.findAllByCustomerCode(newCustomerCode);
        
        
        long count = usersWithSameCode.stream()
            .filter(user -> !user.getId().equals(id))
            .count();

       
        if (count >= 2) {
            return ResponseEntity.status(400).body("Mã khách hàng này đã được sử dụng bởi 2 user!");
        }

       
        User user = userOptional.get();
        user.setCustomerCode(newCustomerCode);

       
        userRepository.save(user);

        return ResponseEntity.ok("Cập nhật mã khách hàng thành công!");
    } catch (Exception e) {
        return ResponseEntity.status(500).body("Lỗi khi cập nhật mã khách hàng: " + e.getMessage());
    }
}
	@GetMapping("/email/{email}")
	public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
	    Optional<User> userOptional = Optional.ofNullable(userRepository.findByEmail(email));
	    
	    if (!userOptional.isPresent()) {
	        return ResponseEntity.status(404).body("Không tìm thấy người dùng với email: " + email);
	    }

	    return ResponseEntity.ok(userOptional.get());
	}

}
