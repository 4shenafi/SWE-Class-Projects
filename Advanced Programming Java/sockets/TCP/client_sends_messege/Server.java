import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        int port = 1235;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");

                InputStream input = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));

                OutputStream output = socket.getOutputStream();
                PrintWriter writer = new PrintWriter(output, true);

                String message;
                while ((message = reader.readLine()) != null) {
                    System.out.println("Received from client: " + message);
                    writer.println("Echo: " + message);
                    if ("bye".equalsIgnoreCase(message)) {
                        break;
                    }
                }

                socket.close();
            }
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
