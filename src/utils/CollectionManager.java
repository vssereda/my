package utils;

import models.StudyGroup;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Hashtable;

//Менеджер для работы с коллекцией StudyGroup

public class CollectionManager {
    private final Hashtable<String, StudyGroup> collection = new Hashtable<>();
    private final FileManager fileManager;
    private final LocalDateTime initDate;

    public CollectionManager(FileManager fileManager) {
        this.fileManager = fileManager;
        this.initDate = LocalDateTime.now(); // Фиксирует время создания
    }

    //Возвращает коллекцию
    public Hashtable<String, StudyGroup> getCollection() {
        return collection;
    }

    //Возвращает дату инициализации коллекции
    public LocalDateTime getInitDate() {
        return initDate;
    }


    //Загружает коллекцию из файла
    // IOException если произошла ошибка ввода-вывода
    public void loadCollection(String filename) throws IOException {
        collection.clear();
        Hashtable<String, StudyGroup> loaded = fileManager.loadFromFile(filename);
        collection.putAll(loaded);
    }

    //Добавляет элемент в коллекцию по ключу с автоупаковкой

    public void add(String key, StudyGroup group) {
        collection.put(key, group);
    }

    //Удаляет элемент из коллекции и возвращает true, если элемент был удален

    public boolean remove(String key) {
        return collection.remove(key) != null;
    }

    //Очищает коллекцию
    public void clear() {
        collection.clear();
    }

    //Возвращает количество элементов в коллекции (размер)

    public int size() {
        return collection.size();
    }
}