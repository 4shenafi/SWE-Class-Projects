package RemoteDateandTime;

import java.rmi.Naming;

public class DateTimeClient {
    public static void main(String[] args) {
        try {
            DateTime obj = (DateTime) Naming.lookup("//localhost/DateTime");
            System.out.println("Current Date and Time: " + obj.getDateTime());
        } catch (Exception e) {
            System.out.println("DateTimeClient exception: " + e);
        }
    }
}

