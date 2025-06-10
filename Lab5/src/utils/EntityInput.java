package utils;

import models.*;
import java.time.LocalDateTime;

public class EntityInput {
    private final ConsoleReader consoleReader;
    private final EnumReader enumReader;
    private final InputValidator validator;

    public EntityInput(ConsoleReader consoleReader, EnumReader enumReader, InputValidator validator) {
        this.consoleReader = consoleReader;
        this.enumReader = enumReader;
        this.validator = validator;
    }

    public ConsoleReader getConsoleReader() {
        return this.consoleReader;
    }

    public EnumReader getEnumReader() {
        return this.enumReader;
    }

    public StudyGroup createStudyGroup(long id) {
        System.out.println("\nВведите данные учебной группы:");

        String name = consoleReader.readString("Название группы", false);
        Coordinates coordinates = createCoordinates();
        Long studentsCount = consoleReader.readLong("Количество студентов", true);
        Integer shouldBeExpelled = consoleReader.readInteger("Количество отчисленных", true);
        FormOfEducation formOfEducation = enumReader.readFormOfEducation();
        Semester semester = enumReader.readSemester();
        Person groupAdmin = createPerson();

        StudyGroup group = new StudyGroup(
                name,
                coordinates,
                studentsCount,
                shouldBeExpelled,
                formOfEducation,
                semester,
                groupAdmin
        );
        group.setId(id); // Устанавливаем id равным переданному ключу
        group.setCreationDate(LocalDateTime.now());
        return group;
    }

    public Person createPerson() {
        System.out.println("\nВведите данные администратора группы (оставьте пусто, чтобы пропустить):");
        String name = consoleReader.readString("Имя администратора", true);
        if (name == null) return null;

        Integer height = consoleReader.readInteger("Рост (целое число > 0)", false);
        Color hairColor = enumReader.readColor();
        Country nationality = enumReader.readCountry();
        Location location = createLocation();

        return new Person(name, height, hairColor, nationality, location);
    }

    public Location createLocation() {
        System.out.println("\nВведите данные локации (оставьте пусто, чтобы пропустить):");
        String name = consoleReader.readString("Название локации", true);
        if (name == null) return null;

        Integer x = consoleReader.readInteger("Координата X (целое число)", false);
        Double y = consoleReader.readDouble("Координата Y (дробное число)", false);
        Long z = consoleReader.readLong("Координата Z", false);

        return new Location(x, y, z, name.isEmpty() ? null : name);
    }

    public Coordinates createCoordinates() {
        System.out.println("Введите координаты:");
        while (true) {
            Integer x = consoleReader.readInteger("Координата X (целое число ≤ 394)", false);
            if (!validator.validateCoordinatesX(x)) {
                System.out.println("Координата X должна быть ≤ 394!");
                continue;
            }
            Long y = consoleReader.readLong("Координата Y", false);

            try {
                return new Coordinates(x, y);
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка в координатах: " + e.getMessage());
            }
        }
    }
}