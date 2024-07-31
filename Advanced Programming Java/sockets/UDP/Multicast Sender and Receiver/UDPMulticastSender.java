import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPMulticastSender {
    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket()) {
            String message = "Multicast message";
            byte[] buffer = message.getBytes();
            InetAddress group = InetAddress.getByName("230.0.0.0");
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, 9876);
            socket.send(packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
