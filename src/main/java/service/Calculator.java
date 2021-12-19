package service;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Calculator {

    public Map<Integer, Integer> initBundleInfo(int[] bundle) {
        Map<Integer, Integer> bundleInfo = new HashMap<>();
        for (int i : bundle) {
            bundleInfo.put(i, 0);
        }
        return bundleInfo;
    }

    public void updateBundleBreakDown(Map<Integer, Map<Integer, Integer>> bundleBreakDown, int i, int j) {
        Map<Integer, Integer> map = new HashMap<>();
        map.putAll(bundleBreakDown.get(i - j));
        bundleBreakDown.put(i, map);
        bundleBreakDown.get(i).replace(j, bundleBreakDown.get(i).get(j) + 1);
    }

    public Map<Integer, Integer> breakDownBundle(int numOfItem, int[] bundle) {
        Map<Integer, Map<Integer, Integer>> bundleBreakdown = new HashMap<>();
        bundleBreakdown.put(0, initBundleInfo(bundle));
        for (int i = 1; i <= numOfItem; i++) {
            int minNumOfBundles = Integer.MAX_VALUE;
            int minNumOfItems = Integer.MAX_VALUE;
            for (int j : bundle) {
                if (j < i) {
                    int numOfBundles = (int) bundleBreakdown.get(i - j).values().stream().collect(Collectors.summarizingInt(Integer::intValue)).getSum();
                    int numOfItems = j;
                    for (int key : bundleBreakdown.get(i - j).keySet()) {
                        numOfItems += key * bundleBreakdown.get(i - j).get(key);
                    }
                    if (numOfItems < minNumOfItems) {
                        minNumOfItems = numOfItems;
                        minNumOfBundles = numOfBundles;
                        updateBundleBreakDown(bundleBreakdown, i, j);
                    }
                    if (numOfItems == minNumOfItems && numOfBundles < minNumOfBundles) {
                        minNumOfBundles = numOfBundles;
                        updateBundleBreakDown(bundleBreakdown, i, j);
                    }
                } else {
                    if (j <= minNumOfItems) {
                        minNumOfItems = j;
                        bundleBreakdown.put(i, initBundleInfo(bundle));
                        bundleBreakdown.get(i).replace(j, 1);
                    }
                }
            }
        }
        return bundleBreakdown.get(numOfItem);
    }

    public Map<Integer, Map<Integer, Double>> calculateOrderPrice(Map<Integer, Integer> bundleMap, Map<Integer, Double> bundleAndPriceMap) {
        Map<Integer, Map<Integer, Double>> priceMap = new HashMap<>();
        Map<Integer, Double> totalPrice = new HashMap<>();
        totalPrice.put(0, 0.0);
        bundleAndPriceMap.forEach((bundle, bundlePrice) -> {
            int numOfBundle = bundleMap.get(bundle);
            totalPrice.put(0, totalPrice.get(0) + bundlePrice * numOfBundle);
        });
        priceMap.put(0, totalPrice);
        bundleAndPriceMap.forEach((bundle, bundlePrice) -> priceMap.put(bundle, new HashMap<Integer, Double>() {
            {
                put(bundleMap.get(bundle), bundlePrice * bundleMap.get(bundle));
            }
        }));
        return priceMap;
    }
}
