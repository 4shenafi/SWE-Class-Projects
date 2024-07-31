package RemoteGreetingService;

import java.rmi.Naming;

public class GreetingClient {
    public static void main(String[] args) {
        try {
            Greeting obj = (Greeting) Naming.lookup("//localhost/Greeting");
            System.out.println(obj.greet("Anteneh"));
            System.out.println(obj.greet("Bernabas"));
        } catch (Exception e) {
            System.out.println("GreetingClient exception: " + e);
        }
    }
}
