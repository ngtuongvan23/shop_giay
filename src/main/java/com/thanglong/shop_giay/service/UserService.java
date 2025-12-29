package com.thanglong.shop_giay.service;

import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thanglong.shop_giay.dto.UserDTO;
import com.thanglong.shop_giay.entity.User;
import com.thanglong.shop_giay.repository.UserRepository;

import jakarta.validation.Valid;



@Service
public class UserService {
    @Autowired
    private UserRepository userRepo;

    // 1. Lấy danh sách tất cả User 
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    // 2. Lấy User theo ID
    public User getUserById(Long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy User với ID: " + id));

    }

    // 3. Tạo mới User ( Đăng kí )
    public User createUser(@Valid UserDTO dto) throws Exception {
        if (userRepo.existsByEmail(dto.getEmail())) { 
             throw new RuntimeException("Email đã được sử dụng.");
        }
        if (userRepo.existsByUserName(dto.getUserName())) { 
             throw new RuntimeException("UserName đã được sử dụng.");
        }
        if (dto.getPassword().length() < 5) {
            throw new RuntimeException("Mật khẩu quá ngắn! Phải từ 5 ký tự trở lên.");
        }

        User user = new User();

        // ???????????
        String hashedPassword = BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt());

        user.setPassword(hashedPassword);
        user.setFullName(dto.getFullName());
        user.setUserName(dto.getUserName());
        user.setEmail(dto.getEmail());
        user.setRole(dto.getRole());
        user.setStatus(true); 

        return userRepo.save(user);
    }

    //4. Đăng nhập
    public User login(String userName, String password) {
        // B1: Tìm user theo userName
        User user = userRepo.findByUserName(userName)
            .orElseThrow(() -> new RuntimeException("Bạn chưa đăng kí tài khoản với userName này."));
        
        // B2: So sánh mật khẩu nhập
        boolean checkPass = BCrypt.checkpw(password, user.getPassword());

        if (!checkPass) {
            throw new RuntimeException("Sai mật khẩu");
        }

        if (!user.isStatus()) {
             throw new RuntimeException("Tài khoản đã bị khóa");
        }

        return user;
    }

    //5. Sửa tài khoản 
    public User updateUser(Long id, UserDTO dto) {
        User existingUser = getUserById(id);

        if (dto.getUserName() != null && !dto.getUserName().isEmpty()) {
            if (!existingUser.getPhoneNumber().equals(dto.getUserName())) {
                if (userRepo.existsByPhoneNumber(dto.getUserName())) {
                    throw new RuntimeException("userName " + dto.getUserName() + "' đã được sử dụng.");
                }
                existingUser.setPhoneNumber(dto.getUserName());
            }
        }

        if (dto.getFullName() != null && !dto.getFullName().isEmpty()) {
            existingUser.setFullName(dto.getFullName());
        }

        if (dto.getUserName() != null && !dto.getUserName().isEmpty()) {
            existingUser.setEmail(dto.getUserName());
        }
        
        // Cập nhật mật khẩu 
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            String hashedPassword = BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt());
            existingUser.setPassword(hashedPassword);
        }
        if (dto.getStatus() != null) {
            existingUser.setStatus(dto.getStatus());
        }
        return userRepo.save(existingUser);
    }

    //6. Delete: chỉ đổi về false.
    public void deleteUser(Long id) {
        User existingUser = getUserById(id);
        existingUser.setStatus(false); 
        userRepo.save(existingUser);
    }

}
