package models;

import java.util.List;

public class FullOrderDescription {
    private Order order;
    private User buyer;  // Используем уже существующий класс User
    private List<OrderItem> orderItems;

    public FullOrderDescription(Order order, User buyer, List<OrderItem> orderItems) {
        this.order = order;
        this.buyer = buyer;
        this.orderItems = orderItems;
    }

    public Order getOrder() {
        return order;
    }

    public User getBuyer() {
        return buyer;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("FullOrderDescription:\n");
        sb.append(order.toString()).append("\n");
        sb.append("Buyer: ").append(buyer.toString()).append("\n");
        sb.append("Order Items:\n");
        // Lambda-выражение для перебора списка orderItems
        orderItems.forEach(item -> sb.append("  ").append(item.toString()).append("\n"));
        return sb.toString();
    }
}
