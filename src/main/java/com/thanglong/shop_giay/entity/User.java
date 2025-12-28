package com.thanglong.shop_giay.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
@Data
@Entity
@Table(name = "Users") 
public class User {

    @Id
    @Column(name = "user_id", length = 50)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 
    
    @Column(name = "userName")
    private String userName ;

    @Column(name = "fullName")
    private String fullName;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number", length = 10)
    private String phoneNumber;

    @Column(name = "role", nullable = false)
    private String role = "user"; // Mặc định là user

    @Column(name = "created_date")
    private LocalDate createdDate; // Chỉ lưu ngày

    @Column(name = "status")
    private boolean status; // trạng thái tài khoản 0 , 1 

    // Tự động lấy ngày hiện tại khi tạo mới

    @PrePersist
    public void prePersist() {
        if (this.createdDate == null) {
            this.createdDate = LocalDate.now();
        }
    }

    public User() {}

}