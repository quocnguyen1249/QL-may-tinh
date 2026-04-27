package com.qlmaytinh.dto.response;

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
public class UserResponse {
	Long id;
	String fullName;
	String username;
	String customercitizenid;
	String phone;
	String email;
	String avatar;
	Long roleId;
	String address;
}
