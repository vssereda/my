package client;

import common.commands.Command;
import common.model.Person;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class Client {
    private static final String HOST = "localhost";
    private static final int PORT = 12345;
    private static final int BUFFER_SIZE = 65536;

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in);
             SocketChannel channel = SocketChannel.open()) {

            channel.configureBlocking(false);
            boolean connected = channel.connect(new InetSocketAddress(HOST, PORT));

            if (!connected) {
                System.out.println("Connecting to server...");
                while (!channel.finishConnect()) {
                    Thread.sleep(100);
                }
            }

            System.out.println("Connected to server. Enter commands (type 'help' for list):");

            while (true) {
                System.out.print("> ");
                if (!scanner.hasNextLine()) continue;

                String input = scanner.nextLine().trim();
                if (input.isEmpty()) continue;

                if (input.equalsIgnoreCase("exit")) {
                    System.out.println("Closing client...");
                    break;
                }

                try {
                    Command command = ClientCommandParser.parse(input);
                    if (command != null) {
                        sendCommand(channel, command);
                        receiveResponse(channel);
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Server is unavailable. Try again later.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Client interrupted");
        }
    }

    private static void sendCommand(SocketChannel channel, Command command) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(command);
        oos.flush();

        byte[] data = baos.toByteArray();
        ByteBuffer buffer = ByteBuffer.wrap(data);

        while (buffer.hasRemaining()) {
            channel.write(buffer);
        }
    }

    private static void receiveResponse(SocketChannel channel) throws IOException, InterruptedException {
        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
        int attempts = 0;
        int maxAttempts = 10;

        while (attempts < maxAttempts) {
            int bytesRead = channel.read(buffer);

            if (bytesRead == -1) {
                throw new IOException("Connection closed by server");
            } else if (bytesRead > 0) {
                buffer.flip();
                ObjectInputStream ois = new ObjectInputStream(
                        new ByteArrayInputStream(buffer.array(), 0, buffer.limit()));
                try {
                    Object response = ois.readObject();
                    System.out.println("Server response:\n" + response);
                } catch (ClassNotFoundException e) {
                    System.err.println("Error deserializing response: " + e.getMessage());
                }
                return;
            }

            attempts++;
            Thread.sleep(200);
        }

        System.out.println("No response from server after " + (maxAttempts * 200) + "ms");
    }
}