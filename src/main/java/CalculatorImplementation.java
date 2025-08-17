import java.rmi.*;
import java.rmi.server.*;
import java.util.*;

public class CalculatorImplementation extends UnicastRemoteObject implements Calculator {
    private final Stack<Integer> list;

    public CalculatorImplementation() throws RemoteException {
        super();
        list = new Stack<>();
    }

    public synchronized void pushValue(int val) throws RemoteException {
        list.push(val);
        System.out.println("Value pushed: " + val);
    }

    public synchronized void pushOperation(String operator) throws RemoteException {
        if (list.isEmpty()) {
            return;
        }

        int result = list.pop();

        while (!list.isEmpty()) {
            int value = list.pop();
            switch (operator) {
                case "min":
                    result = Math.min(result, value);
                    break;
                case "max":
                    result = Math.max(result, value);
                    break;
                case "lcm":
                    result = lcm(result, value);
                    break;
                case "gcd":
                    result = gcd(result, value);
                    break;
                default:
                    throw new RemoteException("Invalid Operator: " + operator);
            }
        }

        list.push(result);
        System.out.println("Operator - " + operator + " result: " + result);
    }

    public synchronized int pop() throws RemoteException {
        if (list.isEmpty()) {
            throw new RemoteException("Stack is empty");
        }

        int result = list.pop();
        System.out.println("Popped Value: " + result);
        return result;
    }

    public synchronized boolean isEmpty() throws RemoteException {
        return list.isEmpty();
    }

    public synchronized int delayPop(int millis) throws RemoteException {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return list.pop();
    }

    private int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    private int lcm(int a, int b) {
        return a * b / gcd(a, b);
    }
}
