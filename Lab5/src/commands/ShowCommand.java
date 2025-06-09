package commands;

import utils.CollectionManager;
import models.StudyGroup;

//вывести в стандартный поток вывода все
// элементы коллекции в строковом представлении

public class ShowCommand implements Command {
    private CollectionManager collectionManager;

    public ShowCommand() {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) {
        for (String key : collectionManager.getCollection().keySet()) {
            StudyGroup group = collectionManager.getCollection().get(key);
            System.out.println("Ключ: " + key + ", Элемент: " + group);
        }
    }

    @Override
    public String getName() {
        return "show";
    }

    @Override
    public String getDescription() {
        return "вывести все элементы коллекции в строковом представлении";
    }
}