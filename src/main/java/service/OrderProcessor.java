package service;

import model.Bundle;
import model.Order;

import java.util.HashMap;
import java.util.Map;

public class OrderProcessor {
    public Map<Integer, Map<Integer, Double>> calculateOrderPrice(Map<Integer, Integer> bundleBreakdown, Map<Integer, Double> bundlesWithPrice) {
        Map<Integer, Map<Integer, Double>> price = new HashMap<>();
        Map<Integer, Double> totalPrice = new HashMap<>();
        totalPrice.put(0, 0.0);
        bundlesWithPrice.forEach((bundle, bundlePrice) -> {
            int numOfBundle = bundleBreakdown.get(bundle);
            totalPrice.put(0, totalPrice.get(0) + bundlePrice * numOfBundle);
        });
        price.put(0, totalPrice);
        bundlesWithPrice.forEach((bundle, bundlePrice) -> price.put(bundle, new HashMap<Integer, Double>() {{
            put(bundleBreakdown.get(bundle), bundlePrice * bundleBreakdown.get(bundle));
        }}));
        return price;
    }

    public Map<String, Map<Integer, Map<Integer, Double>>> orderOutput(Order order, Map<String, Bundle> bundleInfo) {
        Map<String, Map<Integer, Map<Integer, Double>>> orderOutput = new HashMap<>();
        BundleProcessor bundleProcessor = new BundleProcessor();
        order.orderInfo.forEach((formatCode, numOfItems) -> {
            Bundle bundle = bundleInfo.get(formatCode);
            Map<Integer, Integer> divideBundle = bundleProcessor.bundleBreakdown(numOfItems, bundle.getBundle());
            Map<Integer, Map<Integer, Double>> price = calculateOrderPrice(divideBundle, bundle.getBundleInfo());
            orderOutput.put(bundle.getFormatCode(), price);
        });
        return orderOutput;
    }
}
