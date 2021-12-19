package bundleCalculatorTest;
import service.Calculator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;

public class CalculatorTest {
    private final Calculator calculator = new Calculator();
    @Test
    public void testBreakDownBundle(){
        int[] bundle = {3,6,9};
        HashMap<Integer,Integer> expectedResult = new HashMap<Integer,Integer>(){{
           put(3,0);
           put(6,1);
           put(9,1);
        }};
        Map<Integer,Integer> result = calculator.breakDownBundle(15,bundle);
        Assertions.assertEquals(result,expectedResult);
    }
}
