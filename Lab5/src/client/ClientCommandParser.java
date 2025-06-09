package client;

import commands.Command;
import commands.*;
import models.*;
import utils.CollectionManager;

import java.util.Arrays;
import java.util.Scanner;

public class ClientCommandParser {
    public static Command parse(String input) throws IllegalArgumentException {
        String[] parts = input.split("\\s+", 2);
        String command = parts[0].toLowerCase();
        String args = parts.length > 1 ? parts[1] : "";

        switch (command) {
            // Основные команды
            case "help":
                return new HelpCommand();
            case "info":
                return new InfoCommand();
            case "show":
                return new ShowCommand();

            // Команды работы с элементами
            case "insert":
                return new InsertCommand(readStudyGroupFromConsole());
            case "update":
                return new UpdateCommand(parseId(args), readStudyGroupFromConsole());
            case "remove_key":
                return new RemoveKeyCommand(parseId(args));

            // Команды управления коллекцией
            case "clear":
                return new ClearCommand();
            case "save":
                return new SaveCommand(); // Серверная команда
            case "exit":
                return new ExitCommand();

            // Команды сравнения и фильтрации
            case "remove_greater":
                return new RemoveGreaterCommand(readStudyGroupFromConsole());
            case "replace_if_greater":
                return new ReplaceIfGreaterCommand(parseId(args), readStudyGroupFromConsole());
            case "replace_if_lower":
                return new ReplaceIfLowerCommand(parseId(args), readStudyGroupFromConsole());
            case "filter_less_than_semester_enum":
                return new FilterLessThanSemestErenumCommand(parseEnum(args, Semester.class));
            case "filter_greater_than_students_count":
                return new FilterGreaterThanStudentsCountCommand(parseInt(args));

            // Специальные команды вывода
            case "show":
                return new ShowCommand();

            // Работа с скриптами
            case "execute_script":
                return new ExecuteScriptCommand(args);

            default:
                throw new IllegalArgumentException("Неизвестная команда: " + command
                        + ". Введите 'help' для списка команд");
        }
    }

    private static CollectionManager parseId(String args) {
    }

    private static StudyGroup readStudyGroupFromConsole() {
        Scanner scanner = new Scanner(System.in);
        StudyGroup group = new StudyGroup();

        System.out.println("Добавление новой учебной группы:");

        // Ввод основных полей
        System.out.print("Название группы: ");
        group.setName(scanner.nextLine());

        System.out.print("Количество студентов: ");
        group.setStudentsCount(parseInt(scanner.nextLine()));

        System.out.print("Форма обучения (DISTANCE_EDUCATION, FULL_TIME_EDUCATION, EVENING_CLASSES): ");
        group.setFormOfEducation(parseEnum(scanner.nextLine(), FormOfEducation.class));

        System.out.print("Семестр (FIRST, SECOND, THIRD, FOURTH, FIFTH, SIXTH, SEVENTH, EIGHTH): ");
        group.setSemesterEnum(parseEnum(scanner.nextLine(), Semester.class));

        // Ввод координат
        System.out.println("Введите координаты (x y): ");
        Coordinates coordinates = new Coordinates(
                (int) parseLong(scanner.next()),
                parseInt(scanner.next())
        );
        group.setCoordinates(coordinates);
        scanner.nextLine(); // Очистка буфера

        // Ввод админа (Person)
        System.out.println("Введите данные старосты:");
        group.setGroupAdmin(readPersonFromConsole());

        return group;
    }

    private static Person readPersonFromConsole() {
        Scanner scanner = new Scanner(System.in);
        Person person = new Person();

        System.out.println("Добавление нового человека:");

        // Чтение имени
        System.out.print("Имя: ");
        person.setName(scanner.nextLine());

        // Чтение координат
        System.out.print("Координата X (дробное число): ");
        double x = Double.parseDouble(scanner.nextLine());
        System.out.print("Координата Y (дробное число): ");
        float y = Float.parseFloat(scanner.nextLine());
        person.setCoordinates(new Coordinates((int) x, (long) y));

        // Чтение роста
        System.out.print("Рост (целое число > 0): ");
        person.setHeight(Integer.parseInt(scanner.nextLine()));

        // Чтение цвета волос
        System.out.print("Цвет волос (RED, BLACK, BLUE, WHITE, BROWN): ");
        person.setHairColor(Color.valueOf(scanner.nextLine().toUpperCase()));

        // Чтение национальности
        System.out.print("Национальность (RUSSIA, FRANCE, ITALY, ... или null): ");
        String nationality = scanner.nextLine();
        person.setNationality(nationality.equalsIgnoreCase("null") ?
                null : Country.valueOf(nationality.toUpperCase()));

        // Чтение локации
        System.out.print("Локация (в формате 'x,y,z,name' или null): ");
        String location = scanner.nextLine();
        person.setLocation(location.equalsIgnoreCase("null") ?
                null : Location.fromString(location));

        return person;
    }
    private static long parseInt(String arg) {
        try {
            return Integer.parseInt(arg);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Ожидается целое число");
        }
    }

    private static long parseLong(String arg) {
        try {
            return Long.parseLong(arg);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Ожидается длинное целое число");
        }
    }

    private static <T extends Enum<T>> T parseEnum(String arg, Class<T> enumClass) {
        try {
            return Enum.valueOf(enumClass, arg.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Допустимые значения: "
                    + Arrays.toString(enumClass.getEnumConstants()));
        }
    }
}