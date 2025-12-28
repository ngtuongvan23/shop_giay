package com.thanglong.shop_giay.dto;



import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
@Data
public class UserDTO {
	@NotNull(message = "Họ và tên không được để trống")
	private String fullName;

	@JsonProperty("username")
	@NotNull(message = "userName không được để trống")	
    private String userName;

	@NotNull(message = "PassWord không được để trống")
    private String password;

	@NotNull(message = "Email không được để trống")
	private String email;
	
    private String role; 
    private Boolean status;
}
