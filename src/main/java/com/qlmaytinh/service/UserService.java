package com.qlmaytinh.service;

import java.util.List;

import org.mapstruct.factory.Mappers;
import org.mindrot.jbcrypt.BCrypt;

import com.qlmaytinh.dto.request.UserCreationRequest;
import com.qlmaytinh.dto.request.UserUpdateRequest;
import com.qlmaytinh.dto.response.UserResponse;
import com.qlmaytinh.entity.OrderEntity;
import com.qlmaytinh.entity.ProductEntity;
import com.qlmaytinh.entity.UserEntity;
import com.qlmaytinh.mapper.UserMapper;
import com.qlmaytinh.paging.Pageble;
import com.qlmaytinh.repository.OrderRepository;
import com.qlmaytinh.repository.ProductRepository;
import com.qlmaytinh.repository.UserRepository;

public class UserService {

    private final UserRepository userRepository = new UserRepository();
    private final ProductRepository productRepository = new ProductRepository();
    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);
    private OrderRepository orderRepository =  new OrderRepository();
    // CREATE USER
    public UserResponse save(UserCreationRequest request) {
        UserEntity entity = userMapper.toUserEntity(request);
        String hashedPassword = BCrypt.hashpw(request.getPassword(), BCrypt.gensalt());
        entity.setPassword(hashedPassword);

        UserEntity saved = userRepository.findOne(userRepository.save(entity));
        return userMapper.toUserResponse(saved);
    }

    // UPDATE USER
    public UserResponse update(UserUpdateRequest request) {
        UserEntity oldUser = userRepository.findOne(request.getId());
        if (oldUser == null) {
            throw new RuntimeException("Không tìm thấy người dùng");
        }
        userMapper.updateUser(oldUser, request);
        userRepository.update(oldUser);
        return userMapper.toUserResponse(oldUser);
    }

    // FIND BY ID
    public UserResponse findOne(Long id) {
        UserEntity entity = userRepository.findOne(id);
        if (entity == null) {
            throw new RuntimeException("User not found");
        }
        return userMapper.toUserResponse(entity);
    }

    // FIND ALL WITH PAGING
    public List<UserResponse> findAll(Pageble pageble) {
        List<UserEntity> entities = userRepository.findAll(pageble);
        return userMapper.toUserResponseList(entities);
    }
    
    public int countUsers() {
        return userRepository.countUsers();
    }
    
    public UserResponse login(String username, String password) {
        UserEntity user = userRepository.findByUserName(username);
        if (user == null || !BCrypt.checkpw(password, user.getPassword())) {
            throw new RuntimeException("Tên đăng nhập hoặc mật khẩu không hợp lệ");
        }
        return userMapper.toUserResponse(user);
    }

    public UserEntity findByUserNameAndPassword(String username, String password) {
        UserEntity user = userRepository.findByUserName(username);

        if (user != null && BCrypt.checkpw(password, user.getPassword())) {
            return user;
        }
        throw new RuntimeException("Tên hoặc mật khẩu không chính xác");
    }

    public void delete(Long[] ids) {
        for (Long id : ids) {
        		deleteOrdersByUserId(id);
            userRepository.delete(id);
        }
    }
    private void deleteOrdersByUserId(Long userId) {
        List<OrderEntity> orders = orderRepository.findByUserId(userId);

        for (OrderEntity order : orders) {
            if ("PENDING".equals(order.getStatus())) {
                ProductEntity product = productRepository.findOne(order.getProductId());
                if (product != null) {
                    product.setQuantity(product.getQuantity() + order.getQuantity());
                    productRepository.update(product);
                }
            }
        }

        orderRepository.deletesByUserId(userId);
    }

    public boolean isUserInfoComplete(Long userId) {
        UserEntity user = userRepository.findOne(userId);
        if (user == null) return false;

        return isNotBlank(user.getFullName())
            && isNotBlank(user.getPhone())
            && isNotBlank(user.getEmail())
            && isNotBlank(user.getAddress());
    }

    private boolean isNotBlank(String value) {
        return value != null && !value.trim().isEmpty();
    }
}
