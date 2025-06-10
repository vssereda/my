package client.src.main.java.commands;

import models.Semester;
import models.StudyGroup;
import utils.CollectionManager;

import java.util.Arrays;

public class FilterLessThanSemestErenumCommand implements Command {
    private final CollectionManager collectionManager;

    public FilterLessThanSemestErenumCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) {
        // Проверка наличия аргумента
        if (args == null || args.length == 0) {
            System.out.println("Ошибка: требуется указать значение semesterEnum");
            System.out.println("Доступные значения: " + Arrays.toString(Semester.values()));
            return;
        }

        try {
            // Получаем и проверяем значение semesterEnum
            Semester targetSemester = Semester.valueOf(args[0].toUpperCase());
            boolean found = false;

            System.out.println("Элементы с semesterEnum меньше " + targetSemester + ":");

            // проходка по всем элементам коллекции
            for (StudyGroup group : collectionManager.getCollection().values()) {
                if (group.getSemesterEnum() != null &&
                        group.getSemesterEnum().ordinal() < targetSemester.ordinal()) {

                    System.out.println(group);
                    found = true;
                }
            }

            if (!found) {
                System.out.println("Не найдено элементов с semesterEnum меньше " + targetSemester);
            }

        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: неверное значение semesterEnum - '" + args[0] + "'");
            System.out.println("Доступные значения: " + Arrays.toString(Semester.values()));
        }
    }

    private void printAvailableSemesters() {
        System.out.println("Доступные значения: " + Arrays.toString(Semester.values()));
    }

    @Override
    public String getName() {
        return "filter_less_than_semester_enum";
    }

    @Override
    public String getDescription() {
        return "вывести элементы, где semesterEnum меньше заданного";
    }
}