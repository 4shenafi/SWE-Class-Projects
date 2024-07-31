import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class UDPChatServer {
    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket(9876)) {
            final InetAddress[] clientAddress = {null};
            final int[] clientPort = {-1};

            // Thread to handle receiving messages
            Thread receiveThread = new Thread(() -> {
                try {
                    byte[] buffer = new byte[1024];
                    while (true) {
                        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                        socket.receive(packet);
                        clientAddress[0] = packet.getAddress();
                        clientPort[0] = packet.getPort();
                        String receivedMessage = new String(packet.getData(), 0, packet.getLength());
                        System.out.println("Client: " + receivedMessage);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            receiveThread.start();

            // Handle sending messages
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String message = scanner.nextLine();
                if (clientAddress[0] != null && clientPort[0] != -1) {
                    byte[] buffer = message.getBytes();
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length, clientAddress[0], clientPort[0]);
                    socket.send(packet);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
