package com.qlmaytinh.repository;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.qlmaytinh.entity.UserEntity;
import com.qlmaytinh.paging.Pageble;
import com.qlmaytinh.repository.rowmapper.UserRowMapper;

public class UserRepository extends AbstractRepository<UserEntity>{

	public UserEntity findByUserName(String userName) {
	    StringBuilder sql = new StringBuilder("SELECT * FROM user AS u");
	    sql.append(" INNER JOIN role AS r ON r.id = u.roleid");
	    sql.append(" WHERE u.username = ?");
	    List<UserEntity> users = query(sql.toString(), new UserRowMapper(), userName);
	    return users.isEmpty() ? null : users.get(0);
	}



	public Long save(UserEntity userEntity) {
	    StringBuilder sql = new StringBuilder("INSERT INTO user (username, password, fullname, avatar");
	    sql.append(", roleid, customercitizenid, phone, email, address)");
	    sql.append(" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");

	    return insert(sql.toString(),
	            userEntity.getUsername(),
	            userEntity.getPassword(),
	            userEntity.getFullName(),
	            userEntity.getAvatar(),              
	            userEntity.getRoleEntity().getId(),
	            userEntity.getCustomercitizenid(),
	            userEntity.getPhone(),
	            userEntity.getEmail(),
	            userEntity.getAddress()
	    );
	}



	public void update(UserEntity userEntity) {
	    StringBuilder sql = new StringBuilder("UPDATE user SET password = ?, fullname = ?, avatar = ?,");
	    sql.append(" roleid = ?, customercitizenid = ?, phone = ?, email = ?, address = ?");
	    sql.append(" WHERE id = ?");

	    update(sql.toString(),
	            userEntity.getPassword(),
	            userEntity.getFullName(),
	            userEntity.getAvatar(),         
	            userEntity.getRoleEntity().getId(),
	            userEntity.getCustomercitizenid(),
	            userEntity.getPhone(),
	            userEntity.getEmail(),
	            userEntity.getAddress(),
	            userEntity.getId()
	    );
	}



	public void delete(Long id) {
        String sqlAnime = "DELETE FROM user WHERE id = ?";
        update(sqlAnime, id);
	}

	public List<UserEntity> findAll(Pageble pageble) {
		StringBuilder sql = new StringBuilder("SELECT * FROM user AS u");
		sql.append(" INNER JOIN role AS r ON r.id = u.roleid");
		if (pageble != null) {
	        if (pageble.getSorter() != null && StringUtils.isNotBlank(pageble.getSorter().getSortName())) {
	            String sortName = pageble.getSorter().getSortName();
	            String sortBy = pageble.getSorter().getSortBy();

	            if ("RAND()".equalsIgnoreCase(sortName)) {
	                sql.append(" ORDER BY RAND()");
	            } else if (StringUtils.isNotBlank(sortBy)) {
	                sql.append(" ORDER BY ").append(sortName).append(" ").append(sortBy);
	            }
	        }

	        if (pageble.getOffset() != null && pageble.getLimit() != null) {
	            sql.append(" LIMIT ").append(pageble.getOffset())
	               .append(", ").append(pageble.getLimit());
	        }
	    }
		List<UserEntity> userList = query(sql.toString(),new UserRowMapper());
		return userList;
	}


	public UserEntity findOne(Long id) {
		StringBuilder sql = new StringBuilder("SELECT * FROM user AS u");
		sql.append(" INNER JOIN role AS r ON r.id = u.roleid");
		sql.append(" WHERE u.id = ?");
		List<UserEntity> userList = query(sql.toString(),new UserRowMapper(), id);
		return userList.isEmpty() ? null : userList.get(0);
	}
	
	public boolean existsByUserName(String username) {
		String sql = "SELECT COUNT(*) FROM user WHERE username = ?";
		Integer count = count(sql, username);
		return count != null && count > 0; 
	}
	
	public List<UserEntity> findAllByFullNameOrUsername(String keyword, Pageble pageble) {
	    StringBuilder sql = new StringBuilder("SELECT * FROM user AS u");
	    sql.append(" INNER JOIN role AS r ON r.id = u.roleid");
	    sql.append(" WHERE 1 = 1");

	    List<Object> params = new ArrayList<>();

	    if (StringUtils.isNotBlank(keyword)) {
	        sql.append(" AND (u.username LIKE ? OR u.fullname LIKE ?)");
	        String key = "%" + keyword.trim() + "%";
	        params.add(key);
	        params.add(key);
	    }
	    if (pageble != null) {
	        if (pageble.getSorter() != null && StringUtils.isNotBlank(pageble.getSorter().getSortName())) {
	            String sortName = pageble.getSorter().getSortName();
	            String sortBy = pageble.getSorter().getSortBy();

	            if ("RAND()".equalsIgnoreCase(sortName)) {
	                sql.append(" ORDER BY RAND()");
	            } else if (StringUtils.isNotBlank(sortBy)) {
	                sql.append(" ORDER BY ").append(sortName).append(" ").append(sortBy);
	            }
	        }

	        if (pageble.getOffset() != null && pageble.getLimit() != null) {
	            sql.append(" LIMIT ").append(pageble.getOffset())
	               .append(", ").append(pageble.getLimit());
	        }
	    }

	    return query(sql.toString(), new UserRowMapper(), params.toArray());
	}

	public int countUsers() {
	    String sql = "SELECT COUNT(*) FROM user";
	    return count(sql);
	}
}
