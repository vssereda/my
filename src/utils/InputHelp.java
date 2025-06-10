package utils;

import models.*;
import java.util.Scanner;

public class InputHelp {
    private static final EntityInput factory = initFactory();

    private static EntityInput initFactory() {
        Scanner scanner = new Scanner(System.in);
        ConsoleReader consoleReader = new ConsoleReader(scanner);
        EnumReader enumReader = new EnumReader(consoleReader);
        InputValidator validator = new InputValidator();

        return new EntityInput(
                consoleReader,
                enumReader,
                validator
        );
    }

    public static StudyGroup readStudyGroup(long id) {
        return factory.createStudyGroup(id);
    }

    public static Person readPerson() {
        return factory.createPerson();
    }

    public static Location readLocation() {
        return factory.createLocation();
    }

    public static Coordinates readCoordinates() {
        return factory.createCoordinates();
    }

    public static FormOfEducation readFormOfEducation() {
        return factory.getEnumReader().readFormOfEducation();
    }

    public static Semester readSemester() {
        return factory.getEnumReader().readSemester();
    }

    public static Color readColor() {
        return factory.getEnumReader().readColor();
    }

    public static Country readCountry() {
        return factory.getEnumReader().readCountry();
    }

    public static Long readLong(String prompt, boolean nullable) {
        return factory.getConsoleReader().readLong(prompt, nullable);
    }

    public static Integer readInteger(String prompt, boolean nullable) {
        return factory.getConsoleReader().readInteger(prompt, nullable);
    }

    public static Double readDouble(String prompt, boolean nullable) {
        return factory.getConsoleReader().readDouble(prompt, nullable);
    }

    public static String readString(String prompt, boolean nullable) {
        return factory.getConsoleReader().readString(prompt, nullable);
    }
}