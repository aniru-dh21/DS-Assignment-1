import java.rmi.*;

/**
 * This interface defines the remote methods invoked by the clients
 */
public interface Calculator extends Remote {
    void pushValue(int val) throws RemoteException;

    void pushOperation(String operator) throws RemoteException;

    int pop() throws RemoteException;

    boolean isEmpty() throws RemoteException;

    int delayPop(int millis) throws RemoteException;
}
