package Calculator;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;


interface Calculator extends Remote {
    int add(int a, int b) throws RemoteException;
    int subtract(int a, int b) throws RemoteException;
    int multiply(int a, int b) throws RemoteException;
    int divide(int a, int b) throws RemoteException;
}


public class CalculatorImpl extends UnicastRemoteObject implements Calculator {
    protected CalculatorImpl() throws RemoteException {
        super();
    }

    public int add(int a, int b) throws RemoteException {
        return a + b;
    }

    public int subtract(int a, int b) throws RemoteException {
        return a - b;
    }

    public int multiply(int a, int b) throws RemoteException {
        return a * b;
    }

    public int divide(int a, int b) throws RemoteException {
        return a / b;
    }


    public static void main(String[] args) {
        try {
            Calculator calc = new CalculatorImpl();
            Naming.rebind("//localhost/CalculatorService", calc);
            System.out.println("Calculator Service is running...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

