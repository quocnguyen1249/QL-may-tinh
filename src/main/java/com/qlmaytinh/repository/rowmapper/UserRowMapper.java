package com.qlmaytinh.repository.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

import com.qlmaytinh.entity.RoleEntity;
import com.qlmaytinh.entity.UserEntity;

public class UserRowMapper implements RowMapper<UserEntity>{
	private static final Logger LOGGER = Logger.getLogger(UserRowMapper.class.getName());
	@Override
	public UserEntity mapRow(ResultSet rs) throws SQLException {
		try {
			new UserEntity();
			UserEntity user = UserEntity.builder()
								.id(rs.getLong("id"))
								.username(rs.getString("username"))
								.fullName(rs.getString("fullname"))
								.password(rs.getString("password"))
								.avatar(rs.getString("avatar"))
								.customercitizenid(rs.getString("customercitizenid"))
								.phone(rs.getString("phone"))
								.email(rs.getString("email"))
								.address(rs.getString("address"))
								.roleEntity(RoleEntity.builder()
										.id(rs.getLong("roleid"))
										.name(rs.getString("name"))
										.code(rs.getString("code"))
										.build())
								.build();
			return user;
		} catch (Exception e) {
			LOGGER.severe(e.getMessage());
			return null;
		}
	}

}
