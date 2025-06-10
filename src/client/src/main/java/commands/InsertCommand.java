package client.src.main.java.commands;

import models.StudyGroup;
import utils.CollectionManager;
import utils.InputHelp;

public class InsertCommand implements Command {
    private CollectionManager collectionManager;

    public InsertCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 1) {
            System.out.println("Неверное использование: insert key");
            return;
        }

        String key = args[0];
        if (collectionManager.getCollection().containsKey(key)) {
            System.out.println("Элемент с ключом '" + key + "' уже существует");
            return;
        }

        try {
            // Преобразуем ключ в long для использования как ID
            long id = Long.parseLong(key);  // key должен быть числом
            StudyGroup group = InputHelp.readStudyGroup(id);
            collectionManager.add(key, group);
            System.out.println("Элемент успешно добавлен с ключом '" + key + "'");
        } catch (NumberFormatException e) {
            System.out.println("Ключ должен быть числом: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Ошибка при добавлении элемента: " + e.getMessage());
        }
    }

    @Override
    public String getName() {
        return "insert";
    }

    @Override
    public String getDescription() {
        return "добавить новый элемент с заданным ключом";
    }
}