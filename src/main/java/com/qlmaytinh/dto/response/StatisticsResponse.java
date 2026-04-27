package com.qlmaytinh.dto.response;

import java.util.Map;

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
public class StatisticsResponse {
    Integer totalSold;
    Double totalRevenue;
    Integer totalOrders;
    Integer pendingOrders;

    Map<String, Double> revenueByDate;     
    Map<String, Integer> orderStatusRatio;  
    Map<String, Integer> topSellingProducts; 
    Map<String, Double> revenueByBrand;     
}
