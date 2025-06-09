package commands;

import utils.CollectionManager;
import utils.InputHelp;
import models.StudyGroup;

// заменить значение по ключу, если новое значение больше старого

public class ReplaceIfGreaterCommand implements Command {
    private CollectionManager collectionManager;

    public ReplaceIfGreaterCommand(CollectionManager collectionManager, StudyGroup studyGroup) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 1) {
            System.out.println("Неверное использование: replace_if_greater key");
            return;
        }

        String key = args[0];
        StudyGroup oldGroup = collectionManager.getCollection().get(key);

        if (oldGroup == null) {
            System.out.println("Элемент с ключом '" + key + "' не найден.");
            return;
        }

        // сохраняем старый id
        StudyGroup newGroup = InputHelp.readStudyGroup(oldGroup.getId());

        if (newGroup.compareTo(oldGroup) > 0) {
            collectionManager.add(key, newGroup);
            System.out.println("Элемент заменён.");
        } else {
            System.out.println("Элемент не заменён (новое значение не больше старого).");
        }
    }

    @Override
    public String getName() {
        return "replace_if_greater";
    }

    @Override
    public String getDescription() {
        return "Заменить значение по ключу, если новое значение больше старого";
    }
}