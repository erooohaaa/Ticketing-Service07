package dao;

import models.FullOrderDescription;
import models.Order;
import models.OrderItem;
import models.User;
import config.DatabaseConfig;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {
    private static OrderDAOImpl instance;

    private OrderDAOImpl() {}

    public static OrderDAOImpl getInstance() {
        if (instance == null) {
            instance = new OrderDAOImpl();
        }
        return instance;
    }

    @Override
    public FullOrderDescription getFullOrderDescription(int orderId) {
        String sql = "SELECT o.order_id, o.order_date, o.user_id, " +
                "u.username, u.email, " +
                "oi.order_item_id, oi.product_id, oi.quantity, oi.price " +
                "FROM orders o " +
                "JOIN users u ON o.user_id = u.user_id " +
                "LEFT JOIN order_items oi ON o.order_id = oi.order_id " +
                "WHERE o.order_id = ?";
        Order order = null;
        User buyer = null;
        List<OrderItem> orderItems = new ArrayList<>();
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, orderId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    if (order == null) {
                        int oId = rs.getInt("order_id");
                        Date orderDate = rs.getDate("order_date");
                        int userId = rs.getInt("user_id");
                        order = new Order(oId, orderDate, userId);
                        String username = rs.getString("username");
                        String email = rs.getString("email");
                        buyer = new User(userId, username, email);
                    }
                    int orderItemId = rs.getInt("order_item_id");
                    if (orderItemId != 0) {
                        int productId = rs.getInt("product_id");
                        int quantity = rs.getInt("quantity");
                        double price = rs.getDouble("price");
                        OrderItem item = new OrderItem(orderItemId, orderId, productId, quantity, price);
                        orderItems.add(item);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new FullOrderDescription(order, buyer, orderItems);
    }
}
