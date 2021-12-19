import model.Order;
import service.InputProcessor;
import service.OrderFiller;
import service.OutputProcessor;


public class BundleCalculatorApp {
    public static void main(String[] args) {
        InputProcessor inputProcessor = new InputProcessor();
        OutputProcessor outputProcessor = new OutputProcessor();
        OrderFiller orderFiller = new OrderFiller();
        Order order = inputProcessor.getOrderFromUserInput();
        order = orderFiller.fillOrder(order);
        outputProcessor.printOutput(order);
    }
}
