package service;

import model.Order;
import model.OrderItem;

import java.util.Map;

public class OrderFiller {
    private final Calculator calculator = new Calculator();

    public Order fillOrder(Order order) {
        for (OrderItem orderItem : order.getOrderItemsList()) {
            Map<Integer, Integer> bundleMap;
            Map<Integer, Map<Integer, Double>> priceMap;
            Map<Integer, Double> bundleAndPriceMap;
            bundleAndPriceMap = orderItem.getBundleAndPriceMap();
            int numOfItem = Integer.parseInt(orderItem.getNumOfItem());
            int[] bundles = orderItem.getBundle();
            bundleMap = calculator.breakDownBundle(numOfItem, bundles);
            priceMap = calculator.calculateOrderPrice(bundleMap, bundleAndPriceMap);
            orderItem.setItemTotalPrice(priceMap.get(0).get(0));
            orderItem.setBundleMap(bundleMap);
            priceMap.remove(0);
            orderItem.setPriceMap(priceMap);
        }
        return order;
    }
}
