package com.qlmaytinh.mapper;

import com.qlmaytinh.dto.request.OrderCreateRequest;
import com.qlmaytinh.dto.response.OrderResponse;
import com.qlmaytinh.entity.OrderEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-17T14:11:03+0700",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.44.0.v20251118-1623, environment: Java 21.0.9 (Eclipse Adoptium)"
)
public class OrderMapperImpl implements OrderMapper {

    @Override
    public OrderEntity toEntity(OrderCreateRequest request) {
        if ( request == null ) {
            return null;
        }

        OrderEntity.OrderEntityBuilder orderEntity = OrderEntity.builder();

        orderEntity.productId( request.getProductId() );
        orderEntity.quantity( request.getQuantity() );
        orderEntity.userId( request.getUserId() );

        return orderEntity.build();
    }

    @Override
    public OrderResponse toOrderResponse(OrderEntity request) {
        if ( request == null ) {
            return null;
        }

        OrderResponse.OrderResponseBuilder orderResponse = OrderResponse.builder();

        orderResponse.id( request.getId() );
        orderResponse.orderDate( request.getOrderDate() );
        orderResponse.quantity( request.getQuantity() );
        orderResponse.status( request.getStatus() );
        orderResponse.totalAmount( request.getTotalAmount() );

        return orderResponse.build();
    }

    @Override
    public List<OrderResponse> toResponseList(List<OrderEntity> entities) {
        if ( entities == null ) {
            return null;
        }

        List<OrderResponse> list = new ArrayList<OrderResponse>( entities.size() );
        for ( OrderEntity orderEntity : entities ) {
            list.add( toOrderResponse( orderEntity ) );
        }

        return list;
    }
}
