import java.rmi.registry.*;
import java.util.*;

public class CalculatorClient {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            Calculator calculator = (Calculator) registry.lookup("Calculator");

            Scanner sc = new Scanner(System.in);

            while (true) {
                System.out.println("Choose the Option:");
                System.out.println("1. Push Value");
                System.out.println("2. Operation (min, max, lcm, gcd)");
                System.out.println("3. Pop Value");
                System.out.println("4. Check if the stack is empty");
                System.out.println("5. Delay Pop");
                System.out.println("6. Exit");
                System.out.print("Enter the option: ");
                int option = sc.nextInt();

                switch (option) {
                    case 1:
                        System.out.print("Enter Value to be pushed: ");
                        int val = sc.nextInt();
                        calculator.pushValue(val);
                        break;
                    case 2:
                        System.out.print("Enter operation (min, max, lcm, gcd): ");
                        String operation = sc.next();
                        calculator.pushOperation(operation);
                        break;
                    case 3:
                        System.out.println("Popped Value: " + calculator.pop());
                        break;
                    case 4:
                        System.out.println("Is Stack Empty? " + calculator.isEmpty());
                        break;
                    case 5:
                        System.out.print("Enter the delay in milliseconds: ");
                        int millis = sc.nextInt();
                        System.out.println("Delayed Pop Value: " + calculator.delayPop(millis));
                        break;
                    case 6:
                        System.out.println("Exiting the Client...");
                        sc.close();
                        return;
                    default:
                        System.out.println("Invalid Option");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
