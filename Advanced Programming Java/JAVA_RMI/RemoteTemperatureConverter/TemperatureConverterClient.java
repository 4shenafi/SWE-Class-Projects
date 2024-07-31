package RemoteTemperatureConverter;

import java.rmi.Naming;

public class TemperatureConverterClient {
    public static void main(String[] args) {
        try {
            TemperatureConverter obj = (TemperatureConverter) Naming.lookup("//localhost/TemperatureConverter");
            System.out.println("25 Celsius to Fahrenheit: " + obj.celsiusToFahrenheit(25));
            System.out.println("77 Fahrenheit to Celsius: " + obj.fahrenheitToCelsius(77));
        } catch (Exception e) {
            System.out.println("TemperatureConverterClient exception: " + e);
        }
    }
}

