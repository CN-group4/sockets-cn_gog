import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class TcpClient {
    public static void main(String[] args) {
        String serverIp = "100.118.95.40";
        int port = 5000;

        try {
            Socket socket = new Socket(serverIp, port);

            BufferedReader serverReader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8)
            );

            PrintWriter serverWriter = new PrintWriter(
                    new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8),
                    true
            );

            Scanner keyboard = new Scanner(System.in, "UTF-8");

            System.out.println("Connected to TCP server.");

            while (true) {
                System.out.print("Client: ");
                String clientMessage = keyboard.nextLine();

                serverWriter.println(clientMessage);

                if (clientMessage.equalsIgnoreCase("exit")) {
                    System.out.println("Chat ended by client.");
                    break;
                }

                String serverMessage = serverReader.readLine();

                if (serverMessage == null) {
                    System.out.println("Server disconnected.");
                    break;
                }

                System.out.println("Server: " + serverMessage);

                if (serverMessage.equalsIgnoreCase("exit")) {
                    System.out.println("Chat ended by server.");
                    break;
                }
            }

            keyboard.close();
            serverReader.close();
            serverWriter.close();
            socket.close();

            System.out.println("TCP client closed.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}