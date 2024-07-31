import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class UDPFileClient {
    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress address = InetAddress.getByName("localhost");
            byte[] buffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, 9876);
            socket.send(packet);
            socket.receive(packet);
            // Write the received data to "received.txt", create the file if it doesn't exist
            Files.write(Paths.get("received.txt"), packet.getData(), StandardOpenOption.CREATE);
            System.out.println("File received and saved as received.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
