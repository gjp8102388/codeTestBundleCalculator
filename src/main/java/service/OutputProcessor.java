package service;

import model.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OutputProcessor {
    private final Logger logger = LogManager.getLogger(OutputProcessor.class);

    public void printOutput(Order order) {
        logger.info("-----------------------------------------------------------");
        order.getOrderItemsList().forEach(orderItem -> {
            logger.info("{} {} ${}", orderItem.getNumOfItem(), orderItem.getFormatCode(), orderItem.getItemTotalPrice());
            orderItem.getPriceMap().forEach((bundle, v) -> v.forEach((numberOfBundle, price) -> {
                if (numberOfBundle != 0) {
                    logger.info(" {} X {} ${}", numberOfBundle, bundle, price);
                }
            }));
        });
        logger.info("-----------------------------------------------------------");
    }

}
