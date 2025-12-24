package com.thanglong.shop_giay.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
@Data
public class BrandDTO
 {
    @NotNull(message = "Tên hãng không được để trống")
    private String name;
    private String logoUrl;
    public BrandDTO() {
	}
    
}