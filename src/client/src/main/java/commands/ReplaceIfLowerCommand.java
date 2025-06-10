package client.src.main.java.commands;

import models.StudyGroup;
import utils.CollectionManager;
import utils.InputHelp;

// заменить значение по ключу, если новое значение меньше старого

public class ReplaceIfLowerCommand implements Command {
    private CollectionManager collectionManager;

    public ReplaceIfLowerCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 1) {
            System.out.println("Неверное использование: replace_if_lower key");
            return;
        }

        String key = args[0];
        StudyGroup oldGroup = collectionManager.getCollection().get(key);

        if (oldGroup == null) {
            System.out.println("Элемент с ключом '" + key + "' не найден.");
            return;
        }

        try {
            StudyGroup newGroup = InputHelp.readStudyGroup(oldGroup.getId());

            if (newGroup.compareTo(oldGroup) < 0) {
                collectionManager.add(key, newGroup);
                System.out.println("Элемент заменён.");
            } else {
                System.out.println("Элемент не заменён (новое значение не меньше старого).");
            }
        } catch (Exception e) {
            System.out.println("Ошибка при замене элемента: " + e.getMessage());
        }
    }

    @Override
    public String getName() {
        return "replace_if_lowe";
    }

    @Override
    public String getDescription() {
        return "заменить значение по ключу, если новое значение меньше старого";
    }
}