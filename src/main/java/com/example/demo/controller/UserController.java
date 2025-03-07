package com.example.demo.controller;

import com.example.demo.modal.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = {"http://localhost:3000", "http://14.225.29.33:3000" ,"http://zto.com.vn:3000"})
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;
  @Autowired
  UserService userService;
    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();  // Trả về tất cả người dùng
    }
    
    @PutMapping("/{id}/customer-code")
    public ResponseEntity<?> updateCustomerCode(@PathVariable Long id, @RequestBody Map<String, String> payload) {
        try {
            Optional<User> userOptional = userRepository.findById(id);
            if (!userOptional.isPresent()) {
                return ResponseEntity.status(404).body("Không tìm thấy người dùng!");
            }

            User user = userOptional.get();
      
            userRepository.save(user);

            return ResponseEntity.ok("Cập nhật mã khách hàng thành công!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Lỗi khi cập nhật mã khách hàng: " + e.getMessage());
        }
    }

   

}
