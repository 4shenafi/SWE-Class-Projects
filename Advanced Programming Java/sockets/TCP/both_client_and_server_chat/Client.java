import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        String hostname = "localhost";
        int port = 1234;

        try (Socket socket = new Socket(hostname, port)) {
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);

            Scanner scanner = new Scanner(System.in);

            String clientMessage ;

            Thread readThread = new Thread(() -> {
                try {
                    String serverMessage;
                    while ((serverMessage = reader.readLine()) != null) {
                        System.out.println("\nServer: " + serverMessage);

                        if ("bye".equalsIgnoreCase(serverMessage)) {
                            break;
                        }
                    }
                } catch (IOException ex) {
                    System.out.println("Read thread exception: " + ex.getMessage());
                }
            });

            readThread.start();

            while (true) {
                System.out.print("\nClient: ");
                clientMessage = scanner.nextLine();
                writer.println(clientMessage);

                if ("bye".equalsIgnoreCase(clientMessage)) {
                    break;
                }
            }

            readThread.join();
            socket.close();
            scanner.close();
        } catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("I/O error: " + ex.getMessage());
        } catch (InterruptedException ex) {
            System.out.println("Thread interrupted: " + ex.getMessage());
        }
    }
}
