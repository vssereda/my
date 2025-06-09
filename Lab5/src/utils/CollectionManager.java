package utils;

import java.time.LocalDateTime;
import java.util.*;
import models.*;
import java.util.stream.Collectors;

public class CollectionManager {
    private TreeMap<Integer, StudyGroup> collection = new TreeMap<>();
    private LocalDateTime initDate;

    public CollectionManager() {
        this.initDate = LocalDateTime.now();
        // Здесь можно добавить загрузку из файла
    }

    public String info() {
        return "Тип коллекции: " + collection.getClass() + "\n" +
                "Дата инициализации: " + initDate + "\n" +
                "Количество элементов: " + collection.size();
    }

    public String show() {
        if (collection.isEmpty()) return "Коллекция пуста";
        return collection.values().stream()
                .sorted(Comparator.comparing(sg -> sg.getCoordinates().getX()))
                .map(StudyGroup::toString)
                .collect(Collectors.joining("\n"));
    }

    public String insert(Integer key, StudyGroup studyGroup) {
        if (collection.containsKey(key)) {
            return "Элемент с таким ключом уже существует";
        }
        studyGroup.setId(generateId());
        studyGroup.setCreationDate(LocalDateTime.now());
        collection.put(key, studyGroup);
        return "Элемент успешно добавлен";
    }

    public String update(Integer id, StudyGroup studyGroup) {
        Optional<Map.Entry<Integer, StudyGroup>> entry = collection.entrySet().stream()
                .filter(e -> e.getValue().getId().equals(id))
                .findFirst();

        if (entry.isPresent()) {
            studyGroup.setId(id);
            studyGroup.setCreationDate(entry.get().getValue().getCreationDate());
            collection.put(entry.get().getKey(), studyGroup);
            return "Элемент успешно обновлен";
        }
        return "Элемент с таким id не найден";
    }

    public String removeKey(Integer key) {
        if (collection.containsKey(key)) {
            collection.remove(key);
            return "Элемент успешно удален";
        }
        return "Элемент с таким ключом не найден";
    }

    public String clear() {
        collection.clear();
        return "Коллекция очищена";
    }

    public String save() {
        // Реализация сохранения в файл
        return "Коллекция сохранена";
    }

    public String removeGreater(StudyGroup studyGroup) {
        int count = (int) collection.values().stream()
                .filter(sg -> sg.compareTo(studyGroup) > 0)
                .count();
        collection.values().removeIf(sg -> sg.compareTo(studyGroup) > 0);
        return "Удалено элементов: " + count;
    }

    public String removeLower(StudyGroup studyGroup) {
        int count = (int) collection.values().stream()
                .filter(sg -> sg.compareTo(studyGroup) < 0)
                .count();
        collection.values().removeIf(sg -> sg.compareTo(studyGroup) < 0);
        return "Удалено элементов: " + count;
    }

    public String removeAllByShouldBeExpelled(Integer shouldBeExpelled) {
        int count = (int) collection.values().stream()
                .filter(sg -> sg.getShouldBeExpelled().equals(shouldBeExpelled))
                .count();
        collection.values().removeIf(sg -> sg.getShouldBeExpelled().equals(shouldBeExpelled));
        return "Удалено элементов: " + count;
    }

    public String filterLessThanSemesterEnum(Semester semester) {
        return collection.values().stream()
                .filter(sg -> sg.getSemesterEnum().ordinal() < semester.ordinal())
                .map(StudyGroup::toString)
                .collect(Collectors.joining("\n"));
    }

    public String filterGreaterThanGroupAdmin(Person groupAdmin) {
        return collection.values().stream()
                .filter(sg -> sg.getGroupAdmin() != null &&
                        sg.getGroupAdmin().compareTo(groupAdmin) > 0)
                .map(StudyGroup::toString)
                .collect(Collectors.joining("\n"));
    }

    private Integer generateId() {
        return collection.isEmpty() ? 1 :
                Collections.max(collection.values()).getId() + 1;
    }

    public Hashtable<String, StudyGroup> getCollection() {
        return collection;
    }

    public boolean remove(String key) {
    }
}