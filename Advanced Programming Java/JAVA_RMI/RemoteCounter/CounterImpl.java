package RemoteCounter;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CounterImpl extends UnicastRemoteObject implements Counter {
    private int count;

    public CounterImpl() throws RemoteException {
        super();
        count = 0;
    }

    public void increment() throws RemoteException {
        count++;
    }

    public void decrement() throws RemoteException {
        count--;
    }

    public void reset() throws RemoteException {
        count = 0;
    }

    public int getCount() throws RemoteException {
        return count;
    }

    public static void main(String[] args) {
        try {
            CounterImpl obj = new CounterImpl();
            Naming.rebind("Counter", obj);
            System.out.println("Counter Server is ready.");
        } catch (Exception e) {
            System.out.println("Counter Server failed: " + e);
        }
    }
}

interface Counter extends java.rmi.Remote {
    void increment() throws RemoteException;
    void decrement() throws RemoteException;
    void reset() throws RemoteException;
    int getCount() throws RemoteException;
}
