package RemoteStringReverser;

import java.rmi.Naming;

public class StringReverserClient {
    public static void main(String[] args) {
        try {
            StringReverser reverser = (StringReverser) Naming.lookup("//localhost/StringReverser");
            String original = "Hello RMI";
            String reversed = reverser.reverse(original);
            System.out.println("Original: " + original);
            System.out.println("Reversed: " + reversed);
        } catch (Exception e) {
            System.out.println("StringReverserClient exception: " + e);
        }
    }
}

