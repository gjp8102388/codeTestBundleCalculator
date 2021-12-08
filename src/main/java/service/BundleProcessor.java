package service;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class BundleProcessor {

    public Map<Integer, Integer> initBundleInfo(int[] bundle) {
        Map<Integer, Integer> bundleInfo = new HashMap<>();
        for (int i : bundle) {
            bundleInfo.put(i, 0);
        }
        return bundleInfo;
    }

    public void updateBundleBreakdown(Map<Integer, Map<Integer, Integer>> updateBundleBreakdown, int i, int j) {
        Map<Integer, Integer> map = new HashMap<>();
        map.putAll(updateBundleBreakdown.get(i - j));
        updateBundleBreakdown.put(i, map);
        updateBundleBreakdown.get(i).replace(j, updateBundleBreakdown.get(i).get(j) + 1);
    }

    public Map<Integer, Integer> bundleBreakdown(int inputQuantity, int[] bundleInfo) {
        Map<Integer, Map<Integer, Integer>> bundleBreakdown = new HashMap<>();
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
}
