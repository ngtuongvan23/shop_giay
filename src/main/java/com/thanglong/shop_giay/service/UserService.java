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
        
        // Kiểm tra 
        if (userRepo.existsByPhoneNumber(dto.getPhoneNumber())) { 
            throw new RuntimeException("Số điện thoại " + dto.getPhoneNumber() + " đã được dùng.");
        } 
        if (userRepo.existsByEmail(dto.getEmail())) { 
             throw new RuntimeException("Email đã được sử dụng.");
        }
        if (dto.getPassword().length() < 8) {
            throw new RuntimeException("Mật khẩu quá ngắn! Phải từ 8 ký tự trở lên.");
        }

        User user = new User();

        // ???????????
        String hashedPassword = BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt());

        user.setPassword(hashedPassword);
        user.setBirthDate(dto.getBirthDate());
        user.setName(dto.getFullName());
        user.setEmail(dto.getEmail());
        user.setRole(dto.getRole());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setStatus(true); // mặc định là hoạt động 

        return userRepo.save(user);
    }

    //4. Đăng nhập
    public User login(String phoneNumber, String password) {
        // B1: Tìm user theo phoneNumber
        User user = userRepo.findByPhoneNumber(phoneNumber)
            .orElseThrow(() -> new RuntimeException("Bạn chưa đăng kí tài khoản với số điện thoại này hoặc sai số điện thoại."));
        
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

        if (dto.getPhoneNumber() != null && !dto.getPhoneNumber().isEmpty()) {
            if (!existingUser.getPhoneNumber().equals(dto.getPhoneNumber())) {
                if (userRepo.existsByPhoneNumber(dto.getPhoneNumber())) {
                    throw new RuntimeException("PhoneNumber " + dto.getPhoneNumber() + "' đã được sử dụng.");
                }
                existingUser.setPhoneNumber(dto.getPhoneNumber());
            }
        }

        if (dto.getFullName() != null && !dto.getFullName().isEmpty()) {
            existingUser.setName(dto.getFullName());
        }

        if (dto.getEmail() != null && !dto.getEmail().isEmpty()) {
            existingUser.setEmail(dto.getEmail());
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
