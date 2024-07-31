import java.io.*;
import java.net.*;

public class Client {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int PORT = 1234;

    public static void main(String[] args) {
        Socket socket = null;
        BufferedReader inputReader = null;
        PrintWriter outputWriter = null;
        BufferedReader consoleReader = null;

        try {
            socket = new Socket(SERVER_ADDRESS, PORT);
            System.out.println("Connected to server: " + socket);

            inputReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outputWriter = new PrintWriter(socket.getOutputStream(), true);
            consoleReader = new BufferedReader(new InputStreamReader(System.in));

            String command;
            System.out.println("Enter commands to execute on the server (type 'exit' to quit):");

            while ((command = consoleReader.readLine()) != null) {
                outputWriter.println(command);

                if (command.equalsIgnoreCase("exit")) {
                    break;
                }

                String response;
                while ((response = inputReader.readLine()) != null) {
                    System.out.println("Server response: " + response);
                }
            }

            inputReader.close();
            outputWriter.close();
            consoleReader.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
