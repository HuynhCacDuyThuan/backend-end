package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.example.demo.modal.GoogleUser;
import com.example.demo.modal.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

@RestController
@CrossOrigin(origins = {"http://localhost:3000" , "http://14.225.29.33:3000" ,"http://zto.com.vn:3000","https://zto.com.vn","https://api.zto.com.vn"})
@RequestMapping("/login")
public class OAuthController {

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;
    @Autowired
    private UserRepository userRepository;  // Inject UserRepository để lưu vào DB
@Autowired
UserService userService;
    private final RestTemplate restTemplate;

    public OAuthController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping
    public ResponseEntity<?> loginWithGoogle(@RequestBody TokenRequest tokenRequest) {
        String token = tokenRequest.getToken();
        String url = "https://oauth2.googleapis.com/tokeninfo?id_token=" + token;

        try {
            // Gửi yêu cầu đến Google để xác thực token
            GoogleUser googleUser = restTemplate.getForObject(url, GoogleUser.class);

            if (googleUser != null) {
                // Kiểm tra xem người dùng đã tồn tại trong cơ sở dữ liệu chưa
                User existingUser = userRepository.findByEmail(googleUser.getEmail());
                
                if (existingUser == null) {
                    // Nếu người dùng chưa tồn tại, tạo mới và lưu vào DB
                    User newUser = new User();
                    newUser.setSub(googleUser.getSub());
                    newUser.setName(googleUser.getName());
                    newUser.setEmail(googleUser.getEmail());
                    newUser.setPhoneNumber(googleUser.getPhoneNumber());
                    newUser.setPicture(googleUser.getPicture());
                    userRepository.save(newUser);  // Lưu người dùng vào cơ sở dữ liệu
                }

                // Trả về đối tượng với success và thông tin người dùng
                return ResponseEntity.ok(new ApiResponse(true, googleUser));
            } else {
                return ResponseEntity.status(401).body(new ApiResponse(false, "Invalid token"));
            }
        } catch (Exception e) {
            // Nếu có lỗi xảy ra trong quá trình xác thực token, trả về thông báo lỗi
            return ResponseEntity.status(500).body(new ApiResponse(false, "Error verifying token"));
        }
    }
    
   
    // API response model
    public static class ApiResponse {
        private boolean success;
        private Object data;

        public ApiResponse(boolean success, Object data) {
            this.success = success;
            this.data = data;
        }

        public boolean isSuccess() {
            return success;
        }

        public Object getData() {
            return data;
        }
    }
}