package model;

import lombok.Data;

import java.util.Map;

@Data
public class OrderItem {
    private String formatCode;
    private int[] bundle;
    private String numOfItem;
    private Double itemTotalPrice;
    private Map<Integer, Double> bundleAndPriceMap;
    private Map<Integer, Integer> bundleMap;
    private Map<Integer, Map<Integer, Double>> priceMap;

}
