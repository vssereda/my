package client.src.main.java.commands;

import models.StudyGroup;
import utils.CollectionManager;

public class FilterGreaterThanStudentsCountCommand implements Command {
    private final CollectionManager collectionManager;

    public FilterGreaterThanStudentsCountCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) {
        // Проверка наличия аргумента
        if (args == null || args.length < 1) {
            System.out.println("Ошибка: требуется указать значение studentsCount");
            return;
        }

        try {
            long studentsCount = Long.parseLong(args[0]);
            boolean found = false;

            System.out.println("\nЭлементы с studentsCount больше " + studentsCount + ":");
            System.out.println("----------------------------------------");

            // вывод
            for (StudyGroup group : collectionManager.getCollection().values()) {
                if (group.getStudentsCount() != null &&
                        group.getStudentsCount() > studentsCount) {

                    // форматирование
                    System.out.println("ID: " + group.getId());
                    System.out.println("Название: " + group.getName());
                    System.out.println("StudentsCount: " + group.getStudentsCount());
                    if (group.getSemesterEnum() != null) {
                        System.out.println("Semester: " + group.getSemesterEnum());
                    }
                    System.out.println("----------------------------------------");
                    found = true;
                }
            }

            if (!found) {
                System.out.println("Не найдено элементов с studentsCount больше " + studentsCount);
            }

        } catch (NumberFormatException e) {
            System.out.println("Ошибка: некорректное число - '" + args[0] + "'");
        }
    }

    @Override
    public String getName() {
        return "filter_greater_than_students_count";
    }

    @Override
    public String getDescription() {
        return "вывести элементы, значение поля studentsCount которых больше заданного";
    }
}