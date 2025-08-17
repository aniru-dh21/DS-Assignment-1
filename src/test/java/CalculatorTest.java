import org.junit.jupiter.api.*;
import java.rmi.registry.*;
import java.rmi.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CalculatorTest {

    private static Calculator calculator;

    @BeforeAll
    public static void setup() throws RemoteException {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            calculator = (Calculator) registry.lookup("Calculator");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed to connect to RMI registry: " + e.getMessage());
        }
    }

    @Test
    @Order(1)
    public void testPushAndPopSingleClient() throws RemoteException {
        calculator.pushValue(10);
        calculator.pushValue(20);
        assertEquals(20, calculator.pop());
        assertEquals(10, calculator.pop());
    }

    @Test
    @Order(2)
    public void testOperations() throws RemoteException {
        calculator.pushValue(12);
        calculator.pushValue(18);
        calculator.pushOperation("gcd");
        assertEquals(6, calculator.pop());

        calculator.pushValue(4);
        calculator.pushValue(5);
        calculator.pushOperation("lcm");
        assertEquals(20, calculator.pop());
    }

    @Test
    @Order(3)
    public void testDelayPop() throws RemoteException {
        calculator.pushValue(100);
        long start = System.currentTimeMillis();
        int result = calculator.delayPop(500);
        long elapsed = System.currentTimeMillis() - start;
        assertTrue(elapsed >= 500);
        assertEquals(100, result);
    }

    @Test
    @Order(4)
    public void testEmptyStack() throws Exception {
        assertTrue(calculator.isEmpty());
    }
}