import model.Bundle;
import model.Order;
import service.IOProcessor;
import service.OrderProcessor;

import java.util.HashMap;
import java.util.Map;

public class BundleCalculatorApp {
    public static void main(String[] args) {
        IOProcessor ioProcessor = new IOProcessor();
        OrderProcessor orderProcessor = new OrderProcessor();
        Map<String, Bundle> bundleInfo = new HashMap<>();
        Order order = new Order();
        ioProcessor.initializeCalculator(bundleInfo);
        ioProcessor.processInput(order);
        ioProcessor.processOutput(orderProcessor.orderOutput(order, bundleInfo), order);
    }
}
