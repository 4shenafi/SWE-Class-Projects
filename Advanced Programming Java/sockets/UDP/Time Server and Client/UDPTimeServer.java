import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Date;

public class UDPTimeServer {
    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket(9876)) {
            byte[] buffer = new byte[1024];
            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                String currentTime = new Date().toString();
                byte[] timeData = currentTime.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(timeData, timeData.length, packet.getAddress(), packet.getPort());
                socket.send(sendPacket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}