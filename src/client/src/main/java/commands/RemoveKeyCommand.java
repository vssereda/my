package client.src.main.java.commands;

import utils.CollectionManager;

//Команда для удаления элемента из коллекции по ключу

public class RemoveKeyCommand implements Command {
    private final CollectionManager collectionManager;

    public RemoveKeyCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) {
        // Проверка наличия аргумента (ключа)
        if (args.length < 1) {
            System.out.println("Ошибка: Не указан ключ элемента для удаления");
            System.out.println("Правильное использование: " + getName() + " <key>");
            return;
        }

        String key = args[0];

        // Проверка существования элемента
        if (!collectionManager.getCollection().containsKey(key)) {
            System.out.println("Ошибка: Элемент с ключом '" + key + "' не найден в коллекции");
            return;
        }

        // Удаление элемента
        boolean isRemoved = collectionManager.remove(key);

        if (isRemoved) {
            System.out.println("Элемент с ключом '" + key + "' успешно удалён");
        } else {
            System.out.println("Ошибка: Не удалось удалить элемент с ключом '" + key + "'");
        }
    }

    @Override
    public String getName() {
        return "remove_key";
    }

    @Override
    public String getDescription() {
        return "удалить элемент из коллекции по указанному ключу";
    }
}