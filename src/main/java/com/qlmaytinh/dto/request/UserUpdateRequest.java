package com.qlmaytinh.dto.request;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
public class UserUpdateRequest {
	Long id;
	String username;
	@NotBlank(message =  "Tên tài khoản không hợp lệ")
	@Size(min = 8 , message = "Tên tài khoản phải đủ 8 ký tự")
	String fullName;
	String password;
	String avatar;
	String customercitizenid;
	String phone;
	String address;
	String email;
	@NotNull(message =  "Vai trò không hợp lệ không hợp lệ")
	Long roleId;
}
