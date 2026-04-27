import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class UdpClient {
    public static void main(String[] args) {
        String serverIp = "100.118.95.40";
        int serverPort = 5001;

        try {
            DatagramSocket socket = new DatagramSocket();
            InetAddress serverAddress = InetAddress.getByName(serverIp);
            Scanner keyboard = new Scanner(System.in, "UTF-8");

            System.out.println("UDP client started.");

            while (true) {
                System.out.print("Client: ");
                String clientMessage = keyboard.nextLine();

                byte[] sendBuffer = clientMessage.getBytes(StandardCharsets.UTF_8);

                DatagramPacket sendPacket = new DatagramPacket(
                        sendBuffer,
                        sendBuffer.length,
                        serverAddress,
                        serverPort
                );

                socket.send(sendPacket);

                if (clientMessage.equalsIgnoreCase("exit")) {
                    System.out.println("Chat ended by client.");
                    break;
                }

                byte[] receiveBuffer = new byte[1024];

                DatagramPacket receivePacket = new DatagramPacket(
                        receiveBuffer,
                        receiveBuffer.length
                );

                socket.receive(receivePacket);

                String serverMessage = new String(
                        receivePacket.getData(),
                        0,
                        receivePacket.getLength(),
                        StandardCharsets.UTF_8
                );

                System.out.println("Server: " + serverMessage);

                if (serverMessage.equalsIgnoreCase("exit")) {
                    System.out.println("Chat ended by server.");
                    break;
                }
            }

            keyboard.close();
            socket.close();

            System.out.println("UDP client closed.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}