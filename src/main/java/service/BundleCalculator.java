package service;

import java.util.HashMap;
import java.util.stream.Collectors;

public class BundleCalculator {

    public HashMap<Integer, Integer> initBundleInfo(int[] bundle) {
        HashMap<Integer, Integer> bundleInfo = new HashMap<>();
        for (int i : bundle) {
            bundleInfo.put(i, 0);
        }
        return bundleInfo;
    }

    public void updateBundleBreakdown(HashMap<Integer, HashMap<Integer, Integer>> updateBundleBreakdown, int i, int j) {
        HashMap<Integer, Integer> map = (HashMap<Integer, Integer>) updateBundleBreakdown.get(i - j).clone();
        updateBundleBreakdown.put(i, map);
        updateBundleBreakdown.get(i).replace(j, updateBundleBreakdown.get(i).get(j) + 1);
    }

    public HashMap<Integer, Integer> bundleBreakDown(int inputQuantity, int[] bundleInfo) {
        HashMap<Integer, HashMap<Integer, Integer>> bundleBreakdown = new HashMap<>();
        bundleBreakdown.put(0, initBundleInfo(bundleInfo));
        for (int i = 1; i <= inputQuantity; i++) {
            int minNumOfBundles = Integer.MAX_VALUE;
            int minNumOfItems = Integer.MAX_VALUE;
            for (int j : bundleInfo) {
                if (j < i) {
                    int numOfBundles = (int) bundleBreakdown.get(i - j).values().stream().collect(Collectors.summarizingInt(Integer::intValue)).getSum();
                    int numOfItems = j;
                    for (int key : bundleBreakdown.get(i - j).keySet()) {
                        numOfItems += key * bundleBreakdown.get(i - j).get(key);
                    }
                    if (numOfItems < minNumOfItems) {
                        minNumOfItems = numOfItems;
                        minNumOfBundles = numOfBundles;
                        updateBundleBreakdown(bundleBreakdown, i, j);
                    }
                    if (numOfItems == minNumOfItems && numOfBundles < minNumOfBundles) {
                        minNumOfBundles = numOfBundles;
                        updateBundleBreakdown(bundleBreakdown, i, j);
                    }
                } else {
                    if (j <= minNumOfItems) {
                        minNumOfItems = j;
                        bundleBreakdown.put(i, initBundleInfo(bundleInfo));
                        bundleBreakdown.get(i).replace(j, 1);
                    }
                }
            }

        }
        return bundleBreakdown.get(inputQuantity);
    }

    public HashMap<Integer, HashMap<Integer, Double>> calculatePrice(HashMap<Integer, Integer> bundleBreakDown, HashMap<Integer, Double> bundlesWithPrice) {
        HashMap<Integer, HashMap<Integer, Double>> price = new HashMap<>();
        HashMap<Integer, Double> totalPrice = new HashMap<>();
        totalPrice.put(0, 0.0);
        bundlesWithPrice.forEach((bundle, bundlePrice) -> {
            int numOfBundle = bundleBreakDown.get(bundle);
            totalPrice.put(0, totalPrice.get(0) + bundlePrice * numOfBundle);
        });
        price.put(0, totalPrice);
        bundlesWithPrice.forEach((bundle, bundlePrice) -> price.put(bundle, new HashMap<Integer, Double>() {{
            put(bundleBreakDown.get(bundle), bundlePrice * bundleBreakDown.get(bundle));
        }}));
        return price;
    }
}
