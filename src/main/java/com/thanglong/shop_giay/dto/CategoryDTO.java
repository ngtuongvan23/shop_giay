package com.thanglong.shop_giay.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
@Data
public class CategoryDTO {
    @NotNull(message = "Tên danh mục không được để trống")
    String name;
    String description;
    
}
