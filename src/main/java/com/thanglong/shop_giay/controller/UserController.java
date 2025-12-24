package com.thanglong.shop_giay.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thanglong.shop_giay.dto.UserDTO;
import com.thanglong.shop_giay.entity.User;
import com.thanglong.shop_giay.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // 1. Lấy danh sách tất cả User
    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        List<User> list = userService.getAllUsers();
        return ResponseEntity.ok(list);        
    }

    // 2. Lấy User chi tiết theo ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        try {
            User user = userService.getUserById(id);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // 3. Tạo mới User ( đăng kí )
    @PostMapping("/createUser")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO dto) throws Exception {
        try {
            User newUser = userService.createUser(dto);
            return ResponseEntity.ok(newUser);
        } catch (RuntimeException e) {
            // Bắt lỗi trùng Username từ Service ném ra
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    //4. Đăng nhập 
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO request) {
        try {
            // Gọi hàm login bên Service 
            User user = userService.login(request.getPhoneNumber(), request.getPassword());

            // --- QUAN TRỌNG: XỬ LÝ DỮ LIỆU TRẢ VỀ ---
            Map<String, Object> response = new HashMap<>();
            response.put("id", user.getId());
            response.put("username", user.getUsername());
            response.put("name", user.getName());
            response.put("role", user.getRole());
            response.put("phoneNumber", user.getPhoneNumber());
            response.put("message", "Đăng nhập thành công!");

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    // 5. Update User
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserDTO dto) {
        try {
            User updatedUser = userService.updateUser(id, dto);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            // Bắt lỗi không tìm thấy user hoặc trùng username
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 6. Delete (Soft Delete)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok("Đã xóa thành công user có ID: " + id);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
