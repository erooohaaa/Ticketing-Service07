package models;

import java.util.List;
import java.util.stream.Collectors;

public class FullOrderDescription {
    private Order order;
    private User buyer;
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
        String items = orderItems.stream()
                .map(OrderItem::toString)
                .collect(Collectors.joining("\n  ", "  ", "\n"));
        return "FullOrderDescription:\n" +
                order.toString() + "\n" +
                "Buyer: " + buyer.toString() + "\n" +
                "Order Items:\n" + items;
    }
}
