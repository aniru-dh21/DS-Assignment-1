import java.rmi.*;
import java.rmi.server.*;
import java.util.*;

/**
 * It's remote implementation of the Calculator Interface
 */
public class CalculatorImplementation extends UnicastRemoteObject implements Calculator {
    private final Stack<Integer> list;

    public CalculatorImplementation() throws RemoteException {
        super();
        list = new Stack<>();
    }

    /**
     * Pushes Values into the stack
     * @param val
     * @throws RemoteException
     */
    public synchronized void pushValue(int val) throws RemoteException {
        list.push(val);
        System.out.println("Value pushed: " + val);
    }

    /**
     * Runs the operation based on operator input
     * @param operator
     * @throws RemoteException
     */
    public synchronized void pushOperation(String operator) throws RemoteException {
        if (list.isEmpty()) {
            return;
        }

        int result = list.pop();

        while (!list.isEmpty()) {
            int value = list.pop();
            switch (operator) {
                case "min": // Generates minimum of the values in the stack
                    result = Math.min(result, value);
                    break;
                case "max": // Generates maximum of the values in the stack
                    result = Math.max(result, value);
                    break;
                case "lcm": // Generates LCM of the values in the stack
                    result = lcm(result, value);
                    break;
                case "gcd": // Generates GCD of the values in the stack
                    result = gcd(result, value);
                    break;
                default:
                    throw new RemoteException("Invalid Operator: " + operator);
            }
        }

        list.push(result);
        System.out.println("Operator - " + operator + " result: " + result);
    }

    /**
     * Pops the value from the stack
     * @return
     * @throws RemoteException
     */
    public synchronized int pop() throws RemoteException {
        if (list.isEmpty()) {
            throw new RemoteException("Stack is empty");
        }

        int result = list.pop();
        System.out.println("Popped Value: " + result);
        return result;
    }

    /**
     * Checks if the stack is empty or not
     * @return
     * @throws RemoteException
     */
    public synchronized boolean isEmpty() throws RemoteException {
        return list.isEmpty();
    }

    /**
     * It delays pop by milliseconds using Thread method
     * @param millis
     * @return
     * @throws RemoteException
     */
    public synchronized int delayPop(int millis) throws RemoteException {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return list.pop();
    }

    /**
     * Method to generate GCD
     * @param a
     * @param b
     * @return
     */
    private int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    /**
     * Method to generate LCM
     * @param a
     * @param b
     * @return
     */
    private int lcm(int a, int b) {
        return a * b / gcd(a, b);
    }
}
