package client.src.main.java.commands;

import models.StudyGroup;
import utils.CollectionManager;

import java.util.Comparator;
import java.util.Objects;

public class PrintFieldDescendingFormOfEducationCommand implements Command {
    private final CollectionManager collectionManager;

    public PrintFieldDescendingFormOfEducationCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) {
        System.out.println("Значения formOfEducation в порядке убывания:");

        collectionManager.getCollection().values().stream()
                .filter(Objects::nonNull)
                .map(StudyGroup::getFormOfEducation)
                .filter(Objects::nonNull)
                .sorted(Comparator.reverseOrder())
                .distinct()
                .forEach(form -> System.out.println("- " + form));
    }

    @Override
    public String getName() {
        return "print_field_descending_form_of_education";
    }

    @Override
    public String getDescription() {
        return "вывести уникальные значения поля formOfEducation всех элементов в порядке убывания";
    }
}