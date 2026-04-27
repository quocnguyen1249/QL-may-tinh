package com.qlmaytinh.repository.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BrandRowMapper implements RowMapper<String> {

	@Override
	public String mapRow(ResultSet rs) throws SQLException {
		return rs.getString("brand");
	}
}