package commands;

import utils.CollectionManager;
import utils.FileManager;
import java.io.IOException;

// сохранить коллекцию в файл

public class SaveCommand implements Command {
    private final CollectionManager collectionManager;
    private final FileManager fileManager;
    private final String filename;

    public SaveCommand(CollectionManager collectionManager, FileManager fileManager, String filename) {
        this.collectionManager = collectionManager;
        this.fileManager = fileManager;
        this.filename = filename;
    }

    @Override
    public void execute(String[] args) {
        try {
            fileManager.saveToFile(collectionManager.getCollection(), filename);
            System.out.println("Коллекция успешно сохранена в файл: " + filename);
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении: " + e.getMessage());
        }
    }

    @Override
    public String getName() {
        return "save";
    }

    @Override
    public String getDescription() {
        return "сохранить коллекцию в файл";
    }
}