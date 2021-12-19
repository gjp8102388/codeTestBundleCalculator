package model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Order {
    private List<OrderItem> orderItemsList;

    public Order() {
        orderItemsList = new ArrayList<>();
    }

    public void addItemToOrder(OrderItem orderItems) {
        orderItemsList.add(orderItems);
    }
}
