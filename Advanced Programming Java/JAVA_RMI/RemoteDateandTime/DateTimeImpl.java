package RemoteDateandTime;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;


interface DateTime extends java.rmi.Remote {
    String getDateTime() throws RemoteException;
}

public class DateTimeImpl extends UnicastRemoteObject implements DateTime {
    public DateTimeImpl() throws RemoteException {
        super();
    }

    public String getDateTime() throws RemoteException {
        return new Date().toString();
    }

    public static void main(String[] args) {
        try {
            DateTimeImpl obj = new DateTimeImpl();
            Naming.rebind("DateTime", obj);
            System.out.println("DateTime Server is ready.");
        } catch (Exception e) {
            System.out.println("DateTime Server failed: " + e);
        }
    }
}


