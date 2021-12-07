import model.Bundle;
import service.IOProcessor;

import java.util.HashMap;

public class BundleCalculatorApp {
    public static void main(String[] args) {
        IOProcessor iOProcessor = new IOProcessor();
        HashMap<String, Bundle> bundleInfo = new HashMap<>();
        iOProcessor.initializeCalculator(bundleInfo);
        iOProcessor.processInput();
        HashMap<String, Integer> userInput = iOProcessor.getOrder().getOrderInfo();
        iOProcessor.processOutput(userInput, bundleInfo);
    }
}
