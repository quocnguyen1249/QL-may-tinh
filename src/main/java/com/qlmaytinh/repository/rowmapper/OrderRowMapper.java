package com.qlmaytinh.repository.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

import com.qlmaytinh.entity.OrderEntity;

public class OrderRowMapper implements RowMapper<OrderEntity> {
	private static final Logger LOGGER = Logger.getLogger(OrderRowMapper.class.getName());
    @Override
    public OrderEntity mapRow(ResultSet rs) throws SQLException {
    		try {
    			return OrderEntity.builder()
                    .id(rs.getLong("id"))
                    .userId(rs.getLong("userid"))
                    .productId(rs.getLong("productid"))
                    .quantity(rs.getInt("quantity"))
                    .orderDate(rs.getTimestamp("orderdate"))
                    .status(rs.getString("status"))
                    .totalAmount(rs.getDouble("totalamount"))
                    .build();
		} catch (Exception e) {
			LOGGER.severe(e.getMessage());
			return null;
		}
        
    }

}