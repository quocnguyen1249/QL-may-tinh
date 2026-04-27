package com.qlmaytinh.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
	@NotBlank(message =  "Tên tài khoản không hợp lệ")
	@Size(min = 8 , message = "Tên tài khoản phải đủ 8 ký tự")
	String fullName;
	@NotBlank(message = "Tên đăng nhập không hợp lệ")
	@Size(min = 8, message = "Tên đăng nhập phải đủ 8 ký tự")
	String username;
	@NotBlank(message =  "Password không hợp lệ")
	@Size(min = 8 , message = "Password phải đủ 8 ký tự")
	String password;
	String customercitizenid;
	String avatar;
	String phone;
	String address;
	String email;
	@NotNull(message = "Role không được để trống")
    @Positive(message = "Role không hợp lệ")
	Long roleId;
}
