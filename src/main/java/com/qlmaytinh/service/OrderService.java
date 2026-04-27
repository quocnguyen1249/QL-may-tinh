package com.qlmaytinh.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.mapstruct.factory.Mappers;

import com.qlmaytinh.dto.request.OrderCreateRequest;
import com.qlmaytinh.dto.request.OrderUpdateRequest;
import com.qlmaytinh.dto.response.OrderResponse;
import com.qlmaytinh.dto.response.StatisticsResponse;
import com.qlmaytinh.entity.OrderEntity;
import com.qlmaytinh.entity.ProductEntity;
import com.qlmaytinh.mapper.OrderMapper;
import com.qlmaytinh.mapper.ProductMapper;
import com.qlmaytinh.repository.OrderRepository;
import com.qlmaytinh.repository.ProductRepository;

public class OrderService {

    private final OrderRepository orderRepository = new OrderRepository();
    private final ProductRepository productRepository = new ProductRepository();
    private final OrderMapper orderMapper = Mappers.getMapper(OrderMapper.class);
    private final ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);

    public OrderResponse createOrder(OrderCreateRequest request) {
	    ProductEntity product = productRepository.findOne(request.getProductId());
	
	    if (product == null)
	        throw new RuntimeException("Không tìm thấy sản phẩm");

	    if (product.getQuantity() < request.getQuantity())
	        throw new RuntimeException("Số lượng sản phẩm không đủ");
	
	    double finalPrice = product.getPrice() * (1 - product.getDiscount());
	    double totalAmount = finalPrice * request.getQuantity();
	
	    product.setQuantity(product.getQuantity() - request.getQuantity());
	    productRepository.update(product);
	
	    OrderEntity order = orderMapper.toEntity(request);
	    order.setOrderDate(new Timestamp(System.currentTimeMillis()));
	    order.setStatus("PENDING");
	    order.setTotalAmount(totalAmount);
	
	    OrderEntity saved =orderRepository.findOne(orderRepository.save(order));
	    return toOrderResponse(saved, product);
	}


    public void payOrder(Long orderId) {
        OrderEntity order = orderRepository.findOne(orderId);

        if (order == null || !"PENDING".equals(order.getStatus()))
            throw new RuntimeException("Đơn hàng không hợp lệ hoặc không ở trạng thái chờ thanh toán");

        orderRepository.updateStatus(orderId, "PAID");
    }

    public List<OrderResponse> getOrdersByUser(Long userId) {
        List<OrderEntity> entities = orderRepository.findByUserId(userId);
        List<OrderResponse> result = new ArrayList<>();

        for (OrderEntity order : entities) {
            ProductEntity product = productRepository.findOne(order.getProductId());
            result.add(toOrderResponse(order, product));
        }
        return result;
    }


    public void deleteExpiredOrders() {
        List<OrderEntity> orders = orderRepository.findExpiredOrders();
        for (OrderEntity order : orders) {
            if ("PENDING".equals(order.getStatus())) {
                ProductEntity product = productRepository.findOne(order.getProductId());
                if (product != null) {
                    product.setQuantity(product.getQuantity() + order.getQuantity());
                    productRepository.update(product);
                }
            }
            orderRepository.delete(order.getId());
        }
    }
    
    public StatisticsResponse getStatistics() {
        return StatisticsResponse.builder()
                .totalSold(orderRepository.totalSoldProducts())
                .totalRevenue(orderRepository.totalRevenue())
                .totalOrders(orderRepository.totalOrders())
                .pendingOrders(orderRepository.pendingOrders())
                .revenueByDate(orderRepository.revenueByDate())
                .orderStatusRatio(orderRepository.orderStatusRatio())
                .topSellingProducts(orderRepository.topSellingProducts())
                .revenueByBrand(orderRepository.revenueByBrand())
                .build();
    }


    

    public OrderResponse update(OrderUpdateRequest request) {
        OrderEntity order = orderRepository.findOne(request.getId());
        if (order == null) {
            throw new RuntimeException("Đơn hàng không tồn tại");
        }

        if (!"PENDING".equals(order.getStatus())) {
            throw new RuntimeException("Chỉ được cập nhật đơn hàng đang chờ xử lý");
        }

        ProductEntity product = productRepository.findOne(order.getProductId());
        if (product == null) {
            throw new RuntimeException("Không tìm thấy sản phẩm");
        }

        int oldQty = order.getQuantity();
        int newQty = request.getQuantity();
        int diff = newQty - oldQty;

        if (diff > 0) {
        	if (product.getQuantity() < diff) {
                throw new RuntimeException("Số lượng sản phẩm trong kho không đủ");
            }
            product.setQuantity(product.getQuantity() - diff);
        } 
        else if (diff < 0) {
            product.setQuantity(product.getQuantity() + Math.abs(diff));
        }

        productRepository.update(product);

        double finalPrice = product.getPrice() * (1 - product.getDiscount());
        double totalAmount = finalPrice * newQty;

        order.setQuantity(newQty);
        order.setTotalAmount(totalAmount);

        orderRepository.update(order);

        return toOrderResponse(order, product);
    }

    public void delete(Long[] ids) {
        for (Long id : ids) {
        		deleteOrder(id);
        }
    }
    
    private void deleteOrder(Long orderId) {

        OrderEntity order = orderRepository.findOne(orderId);

        if (order == null) throw new RuntimeException("Không tìm thấy đơn hàng!");
        if (!"PENDING".equals(order.getStatus()))throw new RuntimeException("Chỉ đơn hàng chưa thanh toán mới được xóa!");
        ProductEntity product = productRepository.findOne(order.getProductId());
        if (product != null) {
            product.setQuantity(product.getQuantity() + order.getQuantity());
            productRepository.update(product);
        }
        orderRepository.delete(orderId);
    }


	public int countByUserId(Long id) {
		return orderRepository.countByUserId(id);
	}


	public List<OrderResponse> findByUserIdAndStatus(Long id, String status) {
		List<OrderResponse> result = new ArrayList<OrderResponse>();
		List<OrderEntity> orderEntitys = orderRepository.findByUserIdAndStatus(id, status);
		for (OrderEntity orderEntity : orderEntitys) {
			result.add(toOrderResponse(orderEntity, productRepository.findOne(orderEntity.getProductId())));
		}
		return result;
	}
    
	private OrderResponse toOrderResponse(OrderEntity order, ProductEntity product) {
	    OrderResponse result = orderMapper.toOrderResponse(order);
	    result.setProductShortResponse(productMapper.toProductShortResponse(product));
	    return result;
	}
}
