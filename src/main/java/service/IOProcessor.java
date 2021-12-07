package service;

import lombok.Getter;
import model.Bundle;
import model.BundleConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner;

@Getter
public class IOProcessor {
    private final Logger logger = LogManager.getLogger(IOProcessor.class);
    private final String empty = " ";
    private final String dollarSign = " $";
    private final String times = " X ";
    private final HashMap<String, Integer> userInput = new HashMap<>();
    private HashMap<String, Bundle> bundleInfo;

    public HashMap<String, Bundle> initializeCalculator(HashMap<String, Bundle> bundleInfo) {
        Bundle[] bundles = new BundleConfig().setBundleConfig();
        for (Bundle bundle : bundles) {
            bundleInfo.put(bundle.getFormatCode(), bundle);
        }
        this.bundleInfo = bundleInfo;
        logger.info("-----------------------------------------------------------");
        logger.info("Please enter the quantity and the format code: eg. 10 FLAC.");
        logger.info("Enter 'end' to submit.");
        logger.info("-----------------------------------------------------------");
        return bundleInfo;
    }

    public void processInput() {
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
                    userInput.put(inputLine[1], Integer.parseInt(inputLine[0]));
                    input = scanner.nextLine();
                }
                scanner.close();
                break;
            } catch (invalidInputFormat | invalidInputFormatCode e) {
                logger.warn(e.getMessage());
            }
        }
    }

    public void processOutput(HashMap<String, Integer> userInput, HashMap<String, Bundle> bundleInfo) {
        HashMap<String, HashMap<Integer, HashMap<Integer, Double>>> output = new HashMap<>();
        BundleCalculator bundleCalculator = new BundleCalculator();
        userInput.forEach((formatCode, numOfItems) -> {
            Bundle bundle = bundleInfo.get(formatCode);
            HashMap<Integer, Integer> divideBundle = bundleCalculator.bundleBreakDown(numOfItems, bundle.getBundle());
            HashMap<Integer, HashMap<Integer, Double>> price = bundleCalculator.calculatePrice(divideBundle, bundle.getBundleInfo());
            output.put(bundle.getFormatCode(), price);
        });
        logger.info("-----------------------------------------------------------");
        output.forEach((formatCode, price) -> {
            double totalPrice = price.get(0).get(0);
            int quantity = userInput.get(formatCode);
            logger.info(quantity + empty + formatCode + dollarSign + totalPrice);
            price.forEach((bundleKey, bundle) -> bundle.forEach((quantityOfBundle, bundlePrice) -> {
                if (quantityOfBundle != 0) {
                    logger.info(empty + quantityOfBundle + times + bundleKey + dollarSign + bundlePrice);
                }
            }));
        });
        logger.info("-----------------------------------------------------------");
    }
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


