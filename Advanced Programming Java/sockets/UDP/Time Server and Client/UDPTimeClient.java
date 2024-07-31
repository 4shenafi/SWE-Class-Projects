import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPTimeClient {
    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress address = InetAddress.getByName("localhost");
            byte[] buffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, 9876);
            socket.send(packet);
            socket.receive(packet);
            String receivedTime = new String(packet.getData(), 0, packet.getLength());
            System.out.println("Current Time: " + receivedTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}