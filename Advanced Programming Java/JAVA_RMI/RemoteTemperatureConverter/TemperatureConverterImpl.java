package RemoteTemperatureConverter;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class TemperatureConverterImpl extends UnicastRemoteObject implements TemperatureConverter {
    public TemperatureConverterImpl() throws RemoteException {
        super();
    }

    public double celsiusToFahrenheit(double celsius) throws RemoteException {
        return (celsius * 9/5) + 32;
    }

    public double fahrenheitToCelsius(double fahrenheit) throws RemoteException {
        return (fahrenheit - 32) * 5/9;
    }

    public static void main(String[] args) {
        try {
            TemperatureConverterImpl obj = new TemperatureConverterImpl();
            Naming.rebind("TemperatureConverter", obj);
            System.out.println("TemperatureConverter Server is ready.");
        } catch (Exception e) {
            System.out.println("TemperatureConverter Server failed: " + e);
        }
    }
}

interface TemperatureConverter extends java.rmi.Remote {
    double celsiusToFahrenheit(double celsius) throws RemoteException;
    double fahrenheitToCelsius(double fahrenheit) throws RemoteException;
}

