package Calculator;
import java.rmi.*;

public class CalculatorClient {
    public static void main(String[] args) {
        try {
            Calculator stub = (Calculator) Naming.lookup("//localhost/CalculatorService");
            System.out.println("3 + 5 = " + stub.add(3, 5));
            System.out.println("10 - 4 = " + stub.subtract(10, 4));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
