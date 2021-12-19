package model;

import lombok.Data;

import java.util.Arrays;
import java.util.HashMap;

@Data
public class Bundle {
    private final String formatCode;
    private final int[] bundle;
    private final HashMap<Integer, Double> bundleInfo;

    public Bundle(String formatCode, HashMap<Integer, Double> bundleInfo) {
        this.bundle = Arrays.stream(bundleInfo.keySet().toArray(new Integer[0])).mapToInt(Integer::valueOf).toArray();
        this.formatCode = formatCode;
        this.bundleInfo = bundleInfo;
    }

}
