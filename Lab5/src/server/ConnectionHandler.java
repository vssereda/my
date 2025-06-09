package server;

import common.commands.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.CollectionManager;

import java.io.*;
import java.net.Socket;

public class ConnectionHandler extends Thread {
    private static final Logger logger = LoggerFactory.getLogger(ConnectionHandler.class);
    private final Socket clientSocket;
    private final CollectionManager collectionManager;

    public ConnectionHandler(Socket socket, CollectionManager collectionManager) {
        this.clientSocket = socket;
        this.collectionManager = collectionManager;
    }

    @Override
    public void run() {
        try (ObjectInputStream input = new ObjectInputStream(clientSocket.getInputStream());
             ObjectOutputStream output = new ObjectOutputStream(clientSocket.getOutputStream())) {

            logger.info("Новое подключение: {}", clientSocket.getInetAddress());

            while (!clientSocket.isClosed()) {
                Object request = input.readObject();
                if (request instanceof Command) {
                    Command cmd = (Command) request;
                    cmd.setCollectionManager(collectionManager);
                    Object response = cmd.execute();
                    output.writeObject(response);
                    output.flush();
                }
            }
        } catch (Exception e) {
            logger.error("Ошибка обработки соединения", e);
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                logger.error("Ошибка закрытия сокета", e);
            }
        }
    }
}