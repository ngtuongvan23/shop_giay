package com.thanglong.shop_giay.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
@Data
public class UserDTO {

	@NotNull(message = "PassWord không được để trống")
    private String password;
    private String fullName;
    private String email;

	@NotNull(message = "Role không được để trống")
    private String role; 
    private Boolean status;

	@NotNull(message = "Số điện thoại không được để trống")
	@Size(min = 10, max = 10, message = "Số điện thoại phải đúng 10 số")
	@Pattern(regexp = "^0\\d+", message = "Số điện thoại chỉ được chứa số")
	private String phoneNumber;

	@NotNull(message = "Ngày sinh không được để trống")	
	private LocalDate birthDate;
}
