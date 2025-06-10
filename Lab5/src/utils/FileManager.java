package utils;

import models.StudyGroup;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.Hashtable;

// Менеджер для работы с файлами коллекции

public class FileManager {
    private final Gson gson;

    public FileManager() { //создаем экземпляр Gson с отступами для удобства чтения
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()) // Регистрация адаптера
                .setPrettyPrinting()
                .create();
    }

    //Загружает коллекцию из файла
    //IOException если произошла ошибка ввода-вывода

    public Hashtable<String, StudyGroup> loadFromFile(String filename) throws IOException {
        try (Reader reader = new FileReader(filename)) {
            Type type = new TypeToken<Hashtable<String, StudyGroup>>() {
            }.getType();
            Hashtable<String, StudyGroup> loaded = gson.fromJson(reader, type); // преобразуем JSON в Hashtable<String, StudyGroup>
            return loaded != null ? loaded : new Hashtable<>(); // если файл пустой, возвращаем пустую
        } catch (JsonParseException e) {
            throw new IOException("Ошибка парсинга JSON: " + e.getMessage());
        } catch (FileNotFoundException e) {
            throw new IOException("Файл не найден: " + e.getMessage());
        } catch (IOException e) {
            throw new IOException("Ошибка ввода-вывода: " + e.getMessage());
    }

    }

    //Сохраняет коллекцию в файл, автоматически закрывая его, преобразуя Hashtable в JSON
    //IOException если произошла ошибка ввода-вывода

    public void saveToFile(Hashtable<String, StudyGroup> collection, String filename) throws IOException {
        try (Writer writer = new FileWriter(filename)) {
    //        gson.toJson(collection, writer);
            writer.write(gson.toJson(collection));
        }
    }
}