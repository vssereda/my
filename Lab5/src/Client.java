import models.*;

import java.io.*;
import java.net.*;
import java.nio.channels.SocketChannel;
import java.nio.ByteBuffer;
import java.util.Scanner;

public class Client {
    private static final String HOST = "localhost";
    private static final int PORT = 12345;
    private static final int TIMEOUT = 5000;

    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Клиент запущен. Введите 'help' для списка команд.");

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) {
                break;
            }

            if (input.equalsIgnoreCase("help")) {
                printHelp();
                continue;
            }

            try {
                Command command = parseCommand(input, scanner);
                Response response = sendCommand(command);
                System.out.println(response.getMessage());
                if (response.getCollection() != null) {
                    response.getCollection().forEach(System.out::println);
                }
            } catch (IOException e) {
                System.out.println("Ошибка соединения: " + e.getMessage());
            } catch (ClassNotFoundException e) {
                System.out.println("Ошибка обработки ответа: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка ввода: " + e.getMessage());
            }
        }

        scanner.close();
        System.out.println("Клиент завершает работу");
    }

    private Command parseCommand(String input, Scanner scanner) {
        String[] parts = input.split(" ");
        String commandName = parts[0].toLowerCase();

        switch (commandName) {
            case "insert":
            case "update":
            case "remove_greater":
            case "remove_lower":
                return new Command(commandName, parts.length > 1 ? Integer.valueOf(parts[1]) : null);
            case "remove_key":
            case "remove_all_by_should_be_expelled":
                return new Command(commandName, parts.length > 1 ? Integer.parseInt(parts[1]) : null);
            case "filter_less_than_semester_enum":
                return new Command(commandName, parts.length > 1 ? Semester.valueOf(parts[1].toUpperCase()).ordinal() : null);
            case "filter_greater_than_group_admin":
                return new Command(commandName, readPerson(scanner));
            default:
                return new Command(commandName, );
        }
    }

    private StudyGroup readStudyGroup(Scanner scanner) {
        System.out.println("Введите данные учебной группы:");
        StudyGroup sg = new StudyGroup();

        System.out.print("Название: ");
        sg.setName(scanner.nextLine());

        System.out.print("Координата X: ");
        int x = Integer.parseInt(scanner.nextLine());
        System.out.print("Координата Y: ");
        double y = Double.parseDouble(scanner.nextLine());
        sg.setCoordinates(new Coordinates(x, y));

        System.out.print("Количество студентов: ");
        sg.setStudentsCount(Long.parseLong(scanner.nextLine()));

        System.out.print("Должны быть отчислены: ");
        sg.setShouldBeExpelled(Integer.parseInt(scanner.nextLine()));

        System.out.print("Форма обучения (DISTANCE_EDUCATION, FULL_TIME_EDUCATION, EVENING_CLASSES): ");
        String foe = scanner.nextLine();
        sg.setFormOfEducation(foe.isEmpty() ? null : FormOfEducation.valueOf(foe.toUpperCase()));

        System.out.print("Семестр (FIRST-SECOND-THIRD-FOURTH-FIFTH-SIXTH-SEVENTH-EIGHTH): ");
        sg.setSemesterEnum(Semester.valueOf(scanner.nextLine().toUpperCase()));

        System.out.print("Добавить администратора группы? (y/n): ");
        if (scanner.nextLine().equalsIgnoreCase("y")) {
            sg.setGroupAdmin(readPerson(scanner));
        }

        return sg;
    }

    private Person readPerson(Scanner scanner) {
        System.out.println("Введите данные администратора группы:");
        Person p = new Person();

        System.out.print("Имя: ");
        p.setName(scanner.nextLine());

        System.out.print("Рост: ");
        p.setHeight(Integer.parseInt(scanner.nextLine()));

        System.out.print("Цвет волос (GREEN, RED, BLACK, BLUE, BROWN): ");
        String color = scanner.nextLine();
        p.setHairColor(color.isEmpty() ? null : Color.valueOf(color.toUpperCase()));

        System.out.print("Национальность (RUSSIA, FRANCE, ITALY, SOUTH_KOREA): ");
        String country = scanner.nextLine();
        p.setNationality(country.isEmpty() ? null : Country.valueOf(country.toUpperCase()));

        System.out.print("Добавить локацию? (y/n): ");
        if (scanner.nextLine().equalsIgnoreCase("y")) {
            p.setLocation(readLocation(scanner));
        }

        return p;
    }

    private Location readLocation(Scanner scanner) {
        System.out.println("Введите данные локации:");
        Location loc = new Location();

        System.out.print("X: ");
        loc.setX(Integer.parseInt(scanner.nextLine()));

        System.out.print("Y: ");
        loc.setY(Double.parseDouble(scanner.nextLine()));

        System.out.print("Z: ");
        loc.setZ(Integer.parseInt(scanner.nextLine()));

        System.out.print("Название: ");
        loc.setName(scanner.nextLine());

        return loc;
    }

    private Response sendCommand(Command command) throws IOException, ClassNotFoundException {
        try (SocketChannel channel = SocketChannel.open()) {
            channel.configureBlocking(false);
            channel.connect(new InetSocketAddress(HOST, PORT));

            long startTime = System.currentTimeMillis();
            while (!channel.finishConnect()) {
                if (System.currentTimeMillis() - startTime > TIMEOUT) {
                    throw new SocketTimeoutException("Превышено время ожидания подключения");
                }
                try { Thread.sleep(100); } catch (InterruptedException e) {}
            }

            // Сериализация команды
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            ObjectOutputStream objOut = new ObjectOutputStream(byteOut);
            objOut.writeObject(command);
            byte[] commandBytes = byteOut.toByteArray();

            // Отправка данных
            ByteBuffer buffer = ByteBuffer.wrap(commandBytes);
            while (buffer.hasRemaining()) {
                channel.write(buffer);
            }

            // Получение ответа
            buffer = ByteBuffer.allocate(16384); // 16KB буфер
            long bytesRead;
            startTime = System.currentTimeMillis();

            while ((bytesRead = channel.read(buffer)) == 0) {
                if (System.currentTimeMillis() - startTime > TIMEOUT) {
                    throw new SocketTimeoutException("Превышено время ожидания ответа");
                }
                try { Thread.sleep(100); } catch (InterruptedException e) {}
            }

            if (bytesRead == -1) {
                throw new IOException("Соединение закрыто сервером");
            }

            buffer.flip();
            byte[] responseBytes = new byte[buffer.remaining()];
            buffer.get(responseBytes);

            // Десериализация ответа
            ByteArrayInputStream byteIn = new ByteArrayInputStream(responseBytes);
            ObjectInputStream objIn = new ObjectInputStream(byteIn);
            return (Response) objIn.readObject();
        }
    }

    private void printHelp() {
        System.out.println("Доступные команды:");
        System.out.println("info - информация о коллекции");
        System.out.println("show - показать все элементы коллекции");
        System.out.println("insert key {element} - добавить новый элемент с заданным ключом");
        System.out.println("update id {element} - обновить значение элемента по id");
        System.out.println("remove_key key - удалить элемент по ключу");
        System.out.println("clear - очистить коллекцию");
        System.out.println("save - сохранить коллекцию в файл (только сервер)");
        System.out.println("remove_greater {element} - удалить все элементы больше заданного");
        System.out.println("remove_lower {element} - удалить все элементы меньше заданного");
        System.out.println("remove_all_by_should_be_expelled count - удалить все элементы с заданным shouldBeExpelled");
        System.out.println("filter_less_than_semester_enum semester - вывести элементы с semesterEnum меньше заданного");
        System.out.println("filter_greater_than_group_admin {element} - вывести элементы с groupAdmin больше заданного");
        System.out.println("exit - завершить работу клиента");
    }

    public static void main(String[] args) {
        new Client().start();
    }
}