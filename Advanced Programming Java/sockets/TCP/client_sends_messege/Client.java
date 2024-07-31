import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        String hostname = "localhost";
        int port = 1235;

        try (Socket socket = new Socket(hostname, port)) {
            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);

            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            Scanner scanner = new Scanner(System.in);
            String message;

            System.out.println("Enter messages to send to the server (type 'bye' to exit):");

            while (true) {
                System.out.print("Client: ");
                message = scanner.nextLine();
                writer.println(message);

                String response = reader.readLine();
                System.out.println(response);

                if ("bye".equalsIgnoreCase(message)) {
                    break;
                }
            }

            scanner.close();
        } catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("I/O error: " + ex.getMessage());
        }
    }
}
