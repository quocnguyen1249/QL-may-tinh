package com.qlmaytinh.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.qlmaytinh.dto.request.OrderCreateRequest;
import com.qlmaytinh.dto.response.OrderResponse;
import com.qlmaytinh.entity.OrderEntity;

@Mapper
public interface OrderMapper {
	@Mapping(target = "id", ignore = true)
    @Mapping(target = "orderDate", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "totalAmount", ignore = true)
	OrderEntity toEntity(OrderCreateRequest request);
	@Mapping(target = "productShortResponse", ignore = true) 
	OrderResponse toOrderResponse(OrderEntity request);
	
	  List<OrderResponse> toResponseList(List<OrderEntity> entities);
}
