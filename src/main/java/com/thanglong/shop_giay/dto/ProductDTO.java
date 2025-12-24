package com.thanglong.shop_giay.dto;
// hưng dữ liêu JSON mà đc gửi lên
import java.math.BigDecimal;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
@Data
public class ProductDTO {
	@NotNull(message = "Tên sản phẩm không được để trống")
    private String name;

	@NotNull(message = "Giá sản phẩm không được để trống")
	@Min(value = 0, message = "Giá sản phẩm phải dương")
    private BigDecimal price;

    private String description;
	
	@NotNull(message = "Category ID của sản phẩm không được để trống")
    private Integer categoryId;
	@NotNull(message = "Brand ID của sản phẩm không được để trống")
    private Integer brandId;

    
    
}
