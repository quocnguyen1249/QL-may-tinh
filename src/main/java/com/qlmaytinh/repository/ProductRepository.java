package com.qlmaytinh.repository;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.qlmaytinh.entity.ProductEntity;
import com.qlmaytinh.paging.Pageble;
import com.qlmaytinh.repository.rowmapper.BrandRowMapper;
import com.qlmaytinh.repository.rowmapper.ProductRowMapper;

public class ProductRepository extends AbstractRepository<ProductEntity> {

	public Long save(ProductEntity product) {
	    StringBuilder sql = new StringBuilder(
	        "INSERT INTO product (name, brand, description, price, quantity, warrantytime, image, discount)"
	    );
	    sql.append(" VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

	    return insert(sql.toString(),
	            product.getName(),
	            product.getBrand(),
	            product.getDescription(),
	            product.getPrice(),
	            product.getQuantity(),
	            product.getWarrantytime(),
	            product.getImage(),
	            product.getDiscount() == null ? 0.0 : product.getDiscount()
	    );
	}



	public void update(ProductEntity product) {
	    StringBuilder sql = new StringBuilder("UPDATE product SET ");
	    sql.append("name = ?, brand = ?, description = ?, price = ?, ");
	    sql.append("quantity = ?, warrantytime = ?, image = ?, discount = ? ");
	    sql.append("WHERE id = ?");

	    update(sql.toString(),
	            product.getName(),
	            product.getBrand(),
	            product.getDescription(),
	            product.getPrice(),
	            product.getQuantity(),
	            product.getWarrantytime(),
	            product.getImage(),
	            product.getDiscount() == null ? 0.0 : product.getDiscount(),
	            product.getId()
	    );
	}



	public void delete(Long id) {
		String sql = "DELETE FROM product WHERE id = ?";
		update(sql, id);
	}

	public List<ProductEntity> findAll(Pageble pageble) {
		StringBuilder sql = new StringBuilder("SELECT * FROM product");
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
	            sql.append(" LIMIT ").append(pageble.getOffset()).append(", ").append(pageble.getLimit());
	        }
	    }
		return query(sql.toString(), new ProductRowMapper());
	}

	public ProductEntity findOne(Long id) {
		String sql = "SELECT * FROM product WHERE id = ?";
		List<ProductEntity> products = query(sql, new ProductRowMapper(), id);
		return products.isEmpty() ? null : products.get(0);
	}

	public int count() {
		String sql = "SELECT COUNT(*) FROM product";
		Integer count = count(sql);
		return count == null ? 0 : count;
	}

	public int countByBrand(String brand) {
		String sql = "SELECT COUNT(*) FROM product WHERE brand = ?";
		Integer count = count(sql, brand);
		return count == null ? 0 : count;
	}
	
	public List<String> findAllBrands() {
		String sql = "SELECT DISTINCT brand FROM product";
		return query(sql, new BrandRowMapper());
	}

	public boolean existsByName(String name) {
		String sql = "SELECT COUNT(*) FROM product WHERE name = ?";
		Integer count = count(sql, name);
		return count != null && count > 0; 
	}
	public List<ProductEntity> filterProducts(String keyword, List<String> brands,
	        Double minPrice, Double maxPrice,
	        Double minDiscount, Double maxDiscount,
	        List<String> filters,
	        Pageble pageble) {

	    StringBuilder sql = new StringBuilder("SELECT * FROM product WHERE 1 = 1");
	    List<Object> params = new ArrayList<>();

	    // KEYWORD
	    if (StringUtils.isNotBlank(keyword)) {
	        sql.append(" AND name LIKE ?");
	        params.add("%" + keyword.trim() + "%");
	    }

	    // BRAND
	    if (brands != null && !brands.isEmpty()) {
	        sql.append(" AND brand IN (");
	        appendPlaceholders(sql, brands.size());
	        sql.append(")");
	        params.addAll(brands);
	    }

	    // PRICE
	    if (minPrice != null) {
	        sql.append(" AND price >= ?");
	        params.add(minPrice);
	    }
	    if (maxPrice != null) {
	        sql.append(" AND price <= ?");
	        params.add(maxPrice);
	    }

	    // DISCOUNT
	    if (minDiscount != null) {
	        sql.append(" AND discount >= ?");
	        params.add(minDiscount);
	    }
	    if (maxDiscount != null) {
	        sql.append(" AND discount <= ?");
	        params.add(maxDiscount);
	    }

	    // ===== FILTER CPU / RAM / SSD / SCREEN (lọc bằng description LIKE) =====
	    if (filters != null && !filters.isEmpty()) {
	        List<String> cpu = new ArrayList<>();
	        List<String> ram = new ArrayList<>();
	        List<String> ssd = new ArrayList<>();
	        List<String> screen = new ArrayList<>();

	        for (String f : filters) {
	            if (f.startsWith("cpu:")) cpu.add(f.replace("cpu:", ""));
	            if (f.startsWith("ram:")) ram.add(f.replace("ram:", ""));
	            if (f.startsWith("ssd:")) ssd.add(f.replace("ssd:", ""));
	            if (f.startsWith("screen:")) screen.add(f.replace("screen:", ""));
	        }

	        // CPU
	        if (!cpu.isEmpty()) {
	            sql.append(" AND (");
	            for (int i = 0; i < cpu.size(); i++) {
	                sql.append(" description LIKE ? ");
	                if (i < cpu.size() - 1) sql.append(" OR ");
	                params.add("%CPU: " + cpu.get(i) + "%");
	            }
	            sql.append(")");
	        }

	        // RAM
	        if (!ram.isEmpty()) {
	            sql.append(" AND (");
	            for (int i = 0; i < ram.size(); i++) {
	                sql.append(" description LIKE ? ");
	                if (i < ram.size() - 1) sql.append(" OR ");
	                params.add("%RAM: " + ram.get(i) + "%");
	            }
	            sql.append(")");
	        }

	        // SSD
	        if (!ssd.isEmpty()) {
	            sql.append(" AND (");
	            for (int i = 0; i < ssd.size(); i++) {
	                sql.append(" description LIKE ? ");
	                if (i < ssd.size() - 1) sql.append(" OR ");
	                params.add("%SSD: " + ssd.get(i) + "%");
	            }
	            sql.append(")");
	        }

	        // SCREEN
	        if (!screen.isEmpty()) {
	            sql.append(" AND (");
	            for (int i = 0; i < screen.size(); i++) {
	                sql.append(" description LIKE ? ");
	                if (i < screen.size() - 1) sql.append(" OR ");
	                params.add("%Screen: " + screen.get(i) + "%");
	            }
	            sql.append(")");
	        }
	    }

	    sql.append(" AND quantity > 0");

	    // SORT + PAGINATION
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
	            sql.append(" LIMIT ").append(pageble.getOffset()).append(", ").append(pageble.getLimit());
	        }
	    }

	    return query(sql.toString(), new ProductRowMapper(), params.toArray());
	}

	public Integer countFilterProducts(String keyword, List<String> brands,
	        Double minPrice, Double maxPrice,
	        Double minDiscount, Double maxDiscount,
	        List<String> filters) {

	    // ===== KHÔNG CÓ FILTER => COUNT ALL =====
	    boolean noFilter =
	            (keyword == null || keyword.trim().isEmpty())
	            && (brands == null || brands.isEmpty())
	            && minPrice == null
	            && maxPrice == null
	            && minDiscount == null
	            && maxDiscount == null
	            && (filters == null || filters.isEmpty());

	    if (noFilter) {
	        Integer count = count("SELECT COUNT(*) FROM product WHERE quantity > 0");
	        return count == null ? 0 : count;
	    }

	    // ===== CÓ FILTER => BUILD SQL =====
	    StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM product WHERE 1 = 1");
	    List<Object> params = new ArrayList<>();

	    // KEYWORD
	    if (keyword != null && !keyword.trim().isEmpty()) {
	        sql.append(" AND name LIKE ?");
	        params.add("%" + keyword.trim() + "%");
	    }

	    // BRAND
	    if (brands != null && !brands.isEmpty()) {
	        sql.append(" AND brand IN (");
	        appendPlaceholders(sql, brands.size());
	        sql.append(")");
	        params.addAll(brands);
	    }

	    // PRICE
	    if (minPrice != null) {
	        sql.append(" AND price >= ?");
	        params.add(minPrice);
	    }
	    if (maxPrice != null) {
	        sql.append(" AND price <= ?");
	        params.add(maxPrice);
	    }

	    // DISCOUNT
	    if (minDiscount != null) {
	        sql.append(" AND discount >= ?");
	        params.add(minDiscount);
	    }
	    if (maxDiscount != null) {
	        sql.append(" AND discount <= ?");
	        params.add(maxDiscount);
	    }

	    // ===== FILTER CPU / RAM / SSD / SCREEN =====
	    if (filters != null && !filters.isEmpty()) {
	        List<String> cpu = new ArrayList<>();
	        List<String> ram = new ArrayList<>();
	        List<String> ssd = new ArrayList<>();
	        List<String> screen = new ArrayList<>();

	        for (String f : filters) {
	            if (f.startsWith("cpu:")) cpu.add(f.replace("cpu:", ""));
	            if (f.startsWith("ram:")) ram.add(f.replace("ram:", ""));
	            if (f.startsWith("ssd:")) ssd.add(f.replace("ssd:", ""));
	            if (f.startsWith("screen:")) screen.add(f.replace("screen:", ""));
	        }

	        // CPU
	        if (!cpu.isEmpty()) {
	            sql.append(" AND (");
	            for (int i = 0; i < cpu.size(); i++) {
	                sql.append(" description LIKE ? ");
	                if (i < cpu.size() - 1) sql.append(" OR ");
	                params.add("%CPU: " + cpu.get(i) + "%");
	            }
	            sql.append(")");
	        }

	        // RAM
	        if (!ram.isEmpty()) {
	            sql.append(" AND (");
	            for (int i = 0; i < ram.size(); i++) {
	                sql.append(" description LIKE ? ");
	                if (i < ram.size() - 1) sql.append(" OR ");
	                params.add("%RAM: " + ram.get(i) + "%");
	            }
	            sql.append(")");
	        }

	        // SSD
	        if (!ssd.isEmpty()) {
	            sql.append(" AND (");
	            for (int i = 0; i < ssd.size(); i++) {
	                sql.append(" description LIKE ? ");
	                if (i < ssd.size() - 1) sql.append(" OR ");
	                params.add("%SSD: " + ssd.get(i) + "%");
	            }
	            sql.append(")");
	        }

	        // SCREEN
	        if (!screen.isEmpty()) {
	            sql.append(" AND (");
	            for (int i = 0; i < screen.size(); i++) {
	                sql.append(" description LIKE ? ");
	                if (i < screen.size() - 1) sql.append(" OR ");
	                params.add("%Screen: " + screen.get(i) + "%");
	            }
	            sql.append(")");
	        }
	    }

	    sql.append(" AND quantity > 0");

	    Integer count = count(sql.toString(), params.toArray());
	    return count == null ? 0 : count;
	}

	private void appendPlaceholders(StringBuilder sql, int size) {
	    for (int i = 0; i < size; i++) {
	        sql.append("?");
	        if (i < size - 1) sql.append(", ");
	    }
	}
}
