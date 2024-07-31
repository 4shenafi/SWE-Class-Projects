package RemoteGreetingService;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class GreetingImpl extends UnicastRemoteObject implements Greeting {
    public GreetingImpl() throws RemoteException {
        super();
    }

    public String greet(String name) throws RemoteException {
        return "Hello, " + name + "!";
    }

    public static void main(String[] args) {
        try {
            GreetingImpl obj = new GreetingImpl();
            Naming.rebind("Greeting", obj);
            System.out.println("Greeting Server is ready.");
        } catch (Exception e) {
            System.out.println("Greeting Server failed: " + e);
        }
    }
}

interface Greeting extends java.rmi.Remote {
    String greet(String name) throws RemoteException;
}

