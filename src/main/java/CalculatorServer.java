import java.rmi.registry.*;

public class CalculatorServer {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.createRegistry(1099);
            CalculatorImplementation calculator = new CalculatorImplementation();
            registry.rebind("Calculator", calculator);
            System.out.println("Server is running");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
