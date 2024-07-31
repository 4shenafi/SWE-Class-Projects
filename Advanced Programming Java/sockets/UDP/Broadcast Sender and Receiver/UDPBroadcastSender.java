import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class UDPBroadcastSender {
    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket()) {
            socket.setBroadcast(true);
            InetAddress broadcastAddress = InetAddress.getByName("255.255.255.255");
            Scanner scanner = new Scanner(System.in);

            System.out.println("Enter messages to broadcast:");
            while (true) {
                String message = scanner.nextLine();
                byte[] buffer = message.getBytes();
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, broadcastAddress, 9876);
                socket.send(packet);
                System.out.println("Message broadcasted: " + message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}