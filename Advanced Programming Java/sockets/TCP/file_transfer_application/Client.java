import java.io.*;
import java.net.*;

public class Client {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int PORT = 1234;

    public static void main(String[] args) {
        Socket socket = null;
        DataInputStream dataInputStream = null;
        DataOutputStream dataOutputStream = null;
        FileOutputStream fileOutputStream = null;
        BufferedOutputStream bufferedOutputStream = null;

        try {
            socket = new Socket(SERVER_ADDRESS, PORT);
            System.out.println("Connected to server: " + socket);

            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());

            // Request a file
            String fileName = "test.txt"; // specify the file name you want to request
            dataOutputStream.writeUTF(fileName);

            String response = dataInputStream.readUTF();

            if ("File found".equals(response)) {
                long fileSize = dataInputStream.readLong();
                fileOutputStream = new FileOutputStream("received_" + fileName);
                bufferedOutputStream = new BufferedOutputStream(fileOutputStream);

                byte[] buffer = new byte[4096];
                int bytesRead;
                long totalBytesRead = 0;

                while (totalBytesRead < fileSize && (bytesRead = dataInputStream.read(buffer, 0, buffer.length)) != -1) {
                    bufferedOutputStream.write(buffer, 0, bytesRead);
                    totalBytesRead += bytesRead;
                }

                bufferedOutputStream.close();
                fileOutputStream.close();
                System.out.println("File " + fileName + " received successfully.");
            } else {
                System.out.println("Server response: " + response);
            }

            dataInputStream.close();
            dataOutputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
