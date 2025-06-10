package utils;

import java.util.Scanner;

public class ConsoleReader {
    private final Scanner scanner;

    public ConsoleReader(Scanner scanner) {
        this.scanner = scanner;
    }

    public String readString(String prompt, boolean nullable) {
        while (true) {
            System.out.print(prompt + (nullable ? " (или пусто): " : ": "));
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                if (nullable) {
                    return null;
                }
                System.out.println("Значение не может быть пустым!");
                continue;
            }
            return input;
        }
    }

    public Long readLong(String prompt, boolean nullable) {
        while (true) {
            System.out.print(prompt + (nullable ? " (или пусто): " : ": "));
            String input = scanner.nextLine().trim();

            if (input.isEmpty() && nullable) {
                return null;
            }

            try {
                long value = Long.parseLong(input);
                if (value <= 0) {
                    System.out.println("Значение должно быть больше 0!");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Некорректный формат числа! Попробуйте еще раз.");
            }
        }
    }

    public Integer readInteger(String prompt, boolean nullable) {
        while (true) {
            System.out.print(prompt + (nullable ? " (или пусто): " : ": "));
            String input = scanner.nextLine().trim();

            if (input.isEmpty() && nullable) {
                return null;
            }

            try {
                int value = Integer.parseInt(input);
                if (value <= 0) {
                    System.out.println("Значение должно быть больше 0!");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Некорректный формат числа! Попробуйте еще раз.");
            }
        }
    }

    public Double readDouble(String prompt, boolean nullable) {
        while (true) {
            System.out.print(prompt + (nullable ? " (или пусто): " : ": "));
            String input = scanner.nextLine().trim();

            if (input.isEmpty() && nullable) {
                return null;
            }

            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Некорректный формат числа! Попробуйте еще раз.");
            }
        }
    }
}