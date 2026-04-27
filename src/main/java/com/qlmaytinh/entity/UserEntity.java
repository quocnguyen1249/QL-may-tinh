package com.qlmaytinh.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserEntity {
	Long id;
	String fullName;
	String username;
	String password;
	String customercitizenid;
	String phone;
	String email;
	String avatar;
	String address;
	RoleEntity roleEntity;
}
