package models;

import java.sql.Date;

public class Order {
    private int orderId;
    private Date orderDate;
    private int userId;

    public Order(int orderId, Date orderDate, int userId) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.userId = userId;
    }

    public int getOrderId() {
        return orderId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public int getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "Order [orderId=" + orderId + ", orderDate=" + orderDate + ", userId=" + userId + "]";
    }
}
