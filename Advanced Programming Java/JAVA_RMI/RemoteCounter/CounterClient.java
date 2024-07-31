package RemoteCounter;

import java.rmi.Naming;

public class CounterClient {
    public static void main(String[] args) {
        try {
            Counter obj = (Counter) Naming.lookup("//localhost/Counter");
            obj.increment();
            obj.increment();
            System.out.println("Count after incrementing twice: " + obj.getCount());
            obj.decrement();
            System.out.println("Count after decrementing once: " + obj.getCount());
            obj.reset();
            System.out.println("Count after reset: " + obj.getCount());
        } catch (Exception e) {
            System.out.println("CounterClient exception: " + e);
        }
    }
}

