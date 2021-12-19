package service;

import lombok.Data;
import model.Bundle;
import model.BundleConfig;
import model.Order;
import model.OrderItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

@Data
public class InputProcessor {
    private final Logger logger = LogManager.getLogger(InputProcessor.class);
    private final String empty = " ";
    private Map<String, Bundle> bundleInfo = new HashMap<>();

    public Map<String, Bundle> initializeBundleInfo() {
        Bundle[] bundles = new BundleConfig().setBundleConfig();
        for (Bundle bundle : bundles) {
            bundleInfo.put(bundle.getFormatCode(), bundle);
        }
        return bundleInfo;
    }

    public Order getOrderFromUserInput() {
        initializeBundleInfo();
        logger.info("-----------------------------------------------------------");
        logger.info("Please enter the quantity and the format code: eg. 10 FLAC.");
        logger.info("Enter 'end' to submit.");
        logger.info("-----------------------------------------------------------");
        Order order = new Order();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            try {
                String input = scanner.nextLine();
                while (!input.equalsIgnoreCase("end")) {
                    String[] inputLine = input.split(empty);
                    inputLine[1] = inputLine[1].toUpperCase(Locale.ROOT);
                    if (inputLine.length != 2) {
                        throw new invalidInputFormat();
                    }
                    if (!bundleInfo.containsKey(inputLine[1])) {
                        throw new invalidInputFormatCode(inputLine[1]);
                    }
                    order.addItemToOrder(convertUserInputToOrderItem(inputLine));
                    input = scanner.nextLine();
                }
                scanner.close();
                break;
            } catch (invalidInputFormat | invalidInputFormatCode e) {
                logger.warn(e.getMessage());
            }

        }
        return order;
    }

    public OrderItem convertUserInputToOrderItem(String[] userInput) {
        OrderItem orderItems = new OrderItem();
        bundleInfo = initializeBundleInfo();
        String numOfItem = userInput[0];
        String formatCode = userInput[1];
        int[] bundle = bundleInfo.get(formatCode).getBundle();
        Map<Integer, Double> bundleAndPriceMap = bundleInfo.get(formatCode).getBundleInfo();
        orderItems.setFormatCode(formatCode);
        orderItems.setNumOfItem(numOfItem);
        orderItems.setBundle(bundle);
        orderItems.setBundleAndPriceMap(bundleAndPriceMap);
        return orderItems;
    }

    class invalidInputFormat extends Exception {
        public invalidInputFormat() {
            super("Please follow the input format and try again.");
        }
    }

    class invalidInputFormatCode extends Exception {
        public invalidInputFormatCode(String str) {
            super("The format code " + str + " does not exist, please only choose from VID, FLAC and IMG. " + "\n" + "Please try again.");
        }
    }

}

