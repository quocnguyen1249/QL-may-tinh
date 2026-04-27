package com.qlmaytinh.mapper;

import com.qlmaytinh.dto.request.LoginRequest;
import com.qlmaytinh.dto.request.UserCreationRequest;
import com.qlmaytinh.dto.request.UserUpdateRequest;
import com.qlmaytinh.dto.response.LoginResponse;
import com.qlmaytinh.dto.response.UserResponse;
import com.qlmaytinh.entity.RoleEntity;
import com.qlmaytinh.entity.UserEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-17T14:47:39+0700",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.44.0.v20251118-1623, environment: Java 21.0.9 (Eclipse Adoptium)"
)
public class UserMapperImpl implements UserMapper {

    @Override
    public UserEntity toUserEntity(UserCreationRequest request) {
        if ( request == null ) {
            return null;
        }

        UserEntity userEntity = new UserEntity();

        userEntity.setRoleEntity( mapRole( request.getRoleId() ) );
        userEntity.setFullName( request.getFullName() );
        userEntity.setUsername( request.getUsername() );
        userEntity.setPassword( request.getPassword() );
        userEntity.setCustomercitizenid( request.getCustomercitizenid() );
        userEntity.setPhone( request.getPhone() );
        userEntity.setEmail( request.getEmail() );
        userEntity.setAvatar( request.getAvatar() );
        userEntity.setAddress( request.getAddress() );

        return userEntity;
    }

    @Override
    public UserResponse toUserResponse(UserEntity request) {
        if ( request == null ) {
            return null;
        }

        UserResponse userResponse = new UserResponse();

        userResponse.setRoleId( requestRoleEntityId( request ) );
        userResponse.setId( request.getId() );
        userResponse.setFullName( request.getFullName() );
        userResponse.setUsername( request.getUsername() );
        userResponse.setCustomercitizenid( request.getCustomercitizenid() );
        userResponse.setPhone( request.getPhone() );
        userResponse.setEmail( request.getEmail() );
        userResponse.setAvatar( request.getAvatar() );
        userResponse.setAddress( request.getAddress() );

        return userResponse;
    }

    @Override
    public void updateUser(UserEntity user, UserUpdateRequest request) {
        if ( request == null ) {
            return;
        }

        if ( request.getRoleId() != null ) {
            user.setRoleEntity( mapRole( request.getRoleId() ) );
        }
        if ( request.getId() != null ) {
            user.setId( request.getId() );
        }
        if ( request.getFullName() != null ) {
            user.setFullName( request.getFullName() );
        }
        if ( request.getPassword() != null ) {
            user.setPassword( request.getPassword() );
        }
        if ( request.getCustomercitizenid() != null ) {
            user.setCustomercitizenid( request.getCustomercitizenid() );
        }
        if ( request.getPhone() != null ) {
            user.setPhone( request.getPhone() );
        }
        if ( request.getEmail() != null ) {
            user.setEmail( request.getEmail() );
        }
        if ( request.getAvatar() != null ) {
            user.setAvatar( request.getAvatar() );
        }
        if ( request.getAddress() != null ) {
            user.setAddress( request.getAddress() );
        }
    }

    @Override
    public LoginResponse toLoginResponse(LoginRequest request) {
        if ( request == null ) {
            return null;
        }

        LoginResponse loginResponse = new LoginResponse();

        loginResponse.setUsername( request.getUsername() );
        loginResponse.setPassword( request.getPassword() );

        return loginResponse;
    }

    @Override
    public List<UserResponse> toUserResponseList(List<UserEntity> request) {
        if ( request == null ) {
            return null;
        }

        List<UserResponse> list = new ArrayList<UserResponse>( request.size() );
        for ( UserEntity userEntity : request ) {
            list.add( toUserResponse( userEntity ) );
        }

        return list;
    }

    private Long requestRoleEntityId(UserEntity userEntity) {
        if ( userEntity == null ) {
            return null;
        }
        RoleEntity roleEntity = userEntity.getRoleEntity();
        if ( roleEntity == null ) {
            return null;
        }
        Long id = roleEntity.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
