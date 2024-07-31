package RemoteStringReverser;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class StringReverserImpl extends UnicastRemoteObject implements StringReverser {
    public StringReverserImpl() throws RemoteException {
        super();
    }

    public String reverse(String input) throws RemoteException {
        return new StringBuilder(input).reverse().toString();
    }

    public static void main(String[] args) {
        try {
            StringReverserImpl obj = new StringReverserImpl();
            Naming.rebind("StringReverser", obj);
            System.out.println("StringReverser Server is ready.");
        } catch (Exception e) {
            System.out.println("StringReverser Server failed: " + e);
        }
    }
}

interface StringReverser extends java.rmi.Remote {
    String reverse(String input) throws RemoteException;
}

