package com.qlmaytinh.repository.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

import com.qlmaytinh.entity.ProductEntity;

public class ProductRowMapper implements RowMapper<ProductEntity> {

	private static final Logger LOGGER = Logger.getLogger(ProductEntity.class.getName());
	
	@Override
	public ProductEntity mapRow(ResultSet rs) throws SQLException {
		try {
			ProductEntity product = ProductEntity.builder()
									.id(rs.getLong("id"))
									.name(rs.getString("name"))
									.brand(rs.getString("brand"))
									.description(rs.getString("description"))
									.price(rs.getDouble("price"))
									.discount(rs.getDouble("discount"))
									.quantity(rs.getInt("quantity"))
									.image(rs.getString("image"))
									.warrantytime(rs.getInt("warrantytime"))
									.build();
			return product;
		} catch (Exception e) {
			LOGGER.severe(e.getMessage());
			return null;
		}
	}
}
