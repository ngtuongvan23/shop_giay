package com.thanglong.shop_giay.dto;

import lombok.Data;
import java.time.LocalDateTime;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Data 

public class ReviewDTO {
    private Integer id; 
    private String comment;


    @NotNull(message = "Số sao không được để trống")
    @Min(value = 1, message = "Đánh giá thấp nhất là 1 sao")
    @Max(value = 5, message = "Đánh giá cao nhất là 5 sao")
    private Integer rating;

    @NotNull(message = "Mã sản phẩm không được để trống")
    private Integer productId; 
    private String productName;

    @NotNull(message = "Mã khách hàng không được để trống")
    private Long userId;
    private String username; 

    //Thời gian tạo (Chỉ dùng để hiển thị, server tự sinh)
    private LocalDateTime createdDate;

}