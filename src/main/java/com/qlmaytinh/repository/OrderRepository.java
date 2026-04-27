package com.qlmaytinh.repository;

import java.util.List;
import java.util.Map;

import com.qlmaytinh.dto.response.OrderResponse;
import com.qlmaytinh.entity.OrderEntity;
import com.qlmaytinh.repository.rowmapper.OrderRowMapper;

public class OrderRepository extends AbstractRepository<OrderEntity> {

    public Long save(OrderEntity order) {
        String sql = "INSERT INTO orders(productid, userid, quantity, orderdate, status, totalamount) VALUES (?, ?, ?, ?, ?, ?)";
        return insert(sql,
                order.getProductId(),
                order.getUserId(),
                order.getQuantity(),
                order.getOrderDate(),
                order.getStatus(),
                order.getTotalAmount()
        );
    }

    

    public OrderEntity findOne(Long id) {
        String sql = "SELECT * FROM orders WHERE id = ?";
        List<OrderEntity> list = query(sql, new OrderRowMapper(), id);
        return list.isEmpty() ? null : list.get(0);
    }

    public List<OrderEntity> findByUserId(Long userId) {
        String sql = "SELECT * FROM orders WHERE userid = ?";
        return query(sql, new OrderRowMapper(), userId);
    }

    public List<OrderEntity> findExpiredOrders() {
        String sql = """
            SELECT * FROM orders
            WHERE status = 'PENDING'
            AND orderdate <= NOW() - INTERVAL 7 DAY
        """;
        return query(sql, new OrderRowMapper());
    }

    public void updateStatus(Long id, String status) {
        String sql = "UPDATE orders SET status = ? WHERE id = ?";
        update(sql, status, id);
    }

    public Integer totalSoldProducts() {
        String sql = "SELECT COALESCE(SUM(quantity),0) FROM orders WHERE status = 'PAID'";
        return sumInt(sql);
    }

    public Double totalRevenue() {
        String sql = "SELECT COALESCE(SUM(totalamount),0) FROM orders WHERE status = 'PAID'";
        return sumDouble(sql);
    }

    public void deletesByUserId(Long userId) {
        String sql = "DELETE FROM orders WHERE userid = ?";
        update(sql, userId);
    }

    public void deletesByProductId(Long productId) {
        String sql = "DELETE FROM orders WHERE productid = ?";
        update(sql, productId);
    }
    public void delete(Long id) {
        String sql = "DELETE FROM orders WHERE id = ?";
        update(sql, id);
    }

    // ===== DASHBOARD STATISTICS =====

    // tổng số đơn
    public Integer totalOrders() {
        String sql = "SELECT COUNT(*) FROM orders";
        return count(sql);
    }

    // đơn chờ thanh toán
    public Integer pendingOrders() {
        String sql = "SELECT COUNT(*) FROM orders WHERE status = 'PENDING'";
        return count(sql);
    }

    // doanh thu theo ngày
    public Map<String, Double> revenueByDate() {
        String sql = """
            SELECT DATE_FORMAT(orderdate, '%d-%m') as label, SUM(totalamount) as value
            FROM orders
            WHERE status = 'PAID'
            GROUP BY label
            ORDER BY MIN(orderdate)
        """;
        return queryMap(sql); // label -> value
    }

    // tỉ lệ trạng thái đơn
    public Map<String, Integer> orderStatusRatio() {
        String sql = """
            SELECT status as label, COUNT(*) as value
            FROM orders
            GROUP BY status
        """;
        return queryMapInt(sql);
    }

    // top sản phẩm bán chạy
    public Map<String, Integer> topSellingProducts() {
        String sql = """
            SELECT p.name as label, SUM(o.quantity) as value
            FROM orders o
            JOIN product p ON p.id = o.productid
            WHERE o.status = 'PAID'
            GROUP BY p.name
            ORDER BY value DESC
            LIMIT 5
        """;
        return queryMapInt(sql);
    }

    // doanh thu theo hãng
    public Map<String, Double> revenueByBrand() {
        String sql = """
            SELECT p.brand as label, SUM(o.totalamount) as value
            FROM orders o
            JOIN product p ON p.id = o.productid
            WHERE o.status = 'PAID'
            GROUP BY p.brand
        """;
        return queryMap(sql);
    }


    public void update(OrderEntity order) {
        String sql = """
            UPDATE orders
            SET quantity = ?, totalamount = ?, status = ?, orderdate = ?
            WHERE id = ?
        """;
        update(sql,
                order.getQuantity(),
                order.getTotalAmount(),
                order.getStatus(),
                order.getOrderDate(),
                order.getId()
        );
    }

    public List<OrderEntity> findByProductId(Long productId) {
        String sql = "SELECT * FROM orders WHERE productid = ?";
        return query(sql, new OrderRowMapper(), productId);
    }
    public Integer countByUserId(Long userId) {
        String sql = "SELECT COUNT(*) FROM orders WHERE userid = ?";
        return count(sql, userId);
    }



    public List<OrderEntity> findByUserIdAndStatus(Long userId, String status) {
        if (status == null) {
            String sql = "SELECT * FROM orders WHERE userid = ?";
            return query(sql, new OrderRowMapper(), userId);
        }
        String sql = "SELECT * FROM orders WHERE userid = ? AND status = ?";
        return query(sql, new OrderRowMapper(), userId, status);
    }
}
