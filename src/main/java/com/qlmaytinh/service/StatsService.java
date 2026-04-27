package com.qlmaytinh.service;

import java.util.LinkedHashMap;
import java.util.Map;

import com.qlmaytinh.repository.OrderRepository;

public class StatsService {

	private OrderRepository orderRepository = new OrderRepository();

	// Tổng doanh thu theo ngày
	public Map<String, Double> getRevenueByDate() {
	    Map<String, Double> rows = orderRepository.revenueByDate();
	    Map<String, Double> result = new LinkedHashMap<>();
	    for (Map.Entry<String, Double> entry : rows.entrySet()) {
	        result.put(entry.getKey(), entry.getValue());
	    }
	    return result;
	}


    // Tỉ lệ trạng thái đơn
    public Map<String, Long> getOrderStatusRatio() {
        Map<String, Integer> rows = orderRepository.orderStatusRatio();
        Map<String, Long> result = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : rows.entrySet()) {
            result.put(entry.getKey(), entry.getValue().longValue());
        }
        return result;
    }

    // Top sản phẩm bán chạy
    public Map<String, Long> getTopSellingProducts() {
        Map<String, Integer> rows = orderRepository.topSellingProducts();
        Map<String, Long> result = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : rows.entrySet()) {
            result.put(entry.getKey(), entry.getValue().longValue());
        }
        return result;
    }

    // Doanh thu theo hãng
    public Map<String, Double> getRevenueByBrand() {
        return orderRepository.revenueByBrand();
    }
}

