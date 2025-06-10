package utils;

import models.*;
import java.util.Arrays;
import java.util.stream.Collectors;

public class EnumReader {
    private final ConsoleReader consoleReader;

    public EnumReader(ConsoleReader consoleReader) {
        this.consoleReader = consoleReader;
    }

    private <T extends Enum<T>> T readEnum(Class<T> enumType, String prompt) {
        T[] constants = enumType.getEnumConstants();
        if (constants == null) {
            return null;
        }

        String[] enumNames = Arrays.stream(enumType.getEnumConstants())
                .map(Enum::name)
                .toArray(String[]::new);
        System.out.println("\n" + prompt + " (Доступные значения: " +
                String.join(", ", enumNames) + ")");

        while (true) {
            String input = consoleReader.readString("> ", true);
            if (input == null) return null;

            try {
                return Enum.valueOf(enumType, input.trim().toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Некорректное значение! Допустимые варианты: " +
                        Arrays.stream(enumType.getEnumConstants())
                                .map(Enum::name)
                                .collect(Collectors.joining(", ")));
            }
        }
    }

    public FormOfEducation readFormOfEducation() {
        return readEnum(FormOfEducation.class, "Форма обучения");
    }

    public Semester readSemester() {
        return readEnum(Semester.class, "Семестр");
    }

    public Color readColor() {
        return readEnum(Color.class, "Цвет волос");
    }

    public Country readCountry() {
        return readEnum(Country.class, "Страна");
    }
}