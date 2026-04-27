package com.qlmaytinh.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.qlmaytinh.dto.request.LoginRequest;
import com.qlmaytinh.dto.request.UserCreationRequest;
import com.qlmaytinh.dto.request.UserUpdateRequest;
import com.qlmaytinh.dto.response.LoginResponse;
import com.qlmaytinh.dto.response.UserResponse;
import com.qlmaytinh.entity.RoleEntity;
import com.qlmaytinh.entity.UserEntity;

@Mapper(
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface UserMapper {

    @Mapping(target = "roleEntity", source = "roleId")
    @Mapping(target = "id", ignore = true)
    UserEntity toUserEntity(UserCreationRequest request);

    @Mapping(target = "roleId", source = "roleEntity.id")
    UserResponse toUserResponse(UserEntity request);

    @Mapping(target = "roleEntity", source = "roleId")
    @Mapping(target = "username", ignore = true)
    void updateUser(@MappingTarget UserEntity user, UserUpdateRequest request);

    LoginResponse toLoginResponse(LoginRequest request);

    default RoleEntity mapRole(Long roleId) {
        if (roleId == null) return null;
        RoleEntity role = new RoleEntity();
        role.setId(roleId);
        return role;
    }

    List<UserResponse> toUserResponseList(List<UserEntity> request);
}