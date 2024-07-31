import java.io.*;
import java.net.*;

public class Server {
    private static final int PORT = 1234;

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Socket socket = null;
        DataInputStream dataInputStream = null;
        DataOutputStream dataOutputStream = null;
        FileInputStream fileInputStream = null;
        BufferedInputStream bufferedInputStream = null;

        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server started. Waiting for clients...");

            while (true) {
                socket = serverSocket.accept();
                System.out.println("Client connected: " + socket);

                dataInputStream = new DataInputStream(socket.getInputStream());
                dataOutputStream = new DataOutputStream(socket.getOutputStream());

                String fileName = dataInputStream.readUTF();
                File file = new File(fileName);

                if (file.exists() && !file.isDirectory()) {
                    dataOutputStream.writeUTF("File found");
                    dataOutputStream.writeLong(file.length());

                    fileInputStream = new FileInputStream(file);
                    bufferedInputStream = new BufferedInputStream(fileInputStream);

                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = bufferedInputStream.read(buffer, 0, buffer.length)) != -1) {
                        dataOutputStream.write(buffer, 0, bytesRead);
                    }

                    bufferedInputStream.close();
                    fileInputStream.close();
                } else {
                    dataOutputStream.writeUTF("File not found");
                }

                dataInputStream.close();
                dataOutputStream.close();
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
}
