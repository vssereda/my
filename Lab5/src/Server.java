package server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import common.commands.Command;
import common.model.Person;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.stream.Collectors;

public class Server {
    private static final Logger logger = LoggerFactory.getLogger(Server.class);
    private static final int PORT = 12345;
    private static final String COLLECTION_FILE = "collection.xml";

    private static LinkedList<Person> collection = new LinkedList<>();
    private static Date initializationDate;

    public static void main(String[] args) {
        logger.info("Starting server...");
        initializationDate = new Date();

        loadCollection();

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            logger.info("Server started on port {}", PORT);

            while (true) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    logger.info("New connection from {}", clientSocket.getInetAddress());

                    new Thread(new ConnectionHandler(clientSocket)).start();
                } catch (IOException e) {
                    logger.error("Error accepting connection", e);
                }
            }
        } catch (IOException e) {
            logger.error("Server error", e);
        }
    }

    private static void loadCollection() {
        try {
            // Ваш код загрузки коллекции из файла
            logger.info("Collection loaded from file");
        } catch (Exception e) {
            logger.error("Error loading collection", e);
            collection = new LinkedList<>();
        }
    }

    public static void saveCollection() {
        try {
            // Ваш код сохранения коллекции в файл
            logger.info("Collection saved to file");
        } catch (Exception e) {
            logger.error("Error saving collection", e);
        }
    }

    public static LinkedList<Person> getCollection() {
        return collection.stream()
                .sorted(Comparator.comparing(Person::getLocation))
                .collect(Collectors.toCollection(LinkedList::new));
    }

    public static Date getInitializationDate() {
        return initializationDate;
    }
}