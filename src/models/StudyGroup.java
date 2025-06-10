package models;

import java.time.LocalDateTime;

//Класс, представляющий учебную группу.

public class StudyGroup implements Comparable<StudyGroup> {
    private Long id; // Поле не может быть null, значение > 0, уникальное, генерируется автоматически
    private String name; // Поле не может быть null, строка не может быть пустой
    private Coordinates coordinates; // Поле не может быть null
    private LocalDateTime creationDate; // Поле не может быть null, генерируется автоматически
    private Long studentsCount; // Значение поля должно быть > 0, может быть null
    private Integer shouldBeExpelled; // Значение поля должно быть > 0, может быть null
    private FormOfEducation formOfEducation; // Поле может быть null
    private Semester semesterEnum; // Поле не может быть null
    private Person groupAdmin; // Поле может быть null

    private static long idCounter = 1;


    public StudyGroup(String name, Coordinates coordinates, Long studentsCount,
                      Integer shouldBeExpelled, FormOfEducation formOfEducation,
                      Semester semesterEnum, Person groupAdmin) {
        this.id = generateId();
        this.creationDate = LocalDateTime.now();
        setName(name);
        setCoordinates(coordinates);
        setStudentsCount(studentsCount);
        setShouldBeExpelled(shouldBeExpelled);
        setFormOfEducation(formOfEducation);
        setSemesterEnum(semesterEnum);
        setGroupAdmin(groupAdmin);
    }


    //Генерация уникального ID для группы\
    private static synchronized long generateId() {
        return idCounter++;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID должен быть положительным числом и не может быть null-значением");
        }
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Поле Имя не может быть null-значением");
        }
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        if (coordinates == null) {
            throw new IllegalArgumentException("Поле Координаты не может быть null-значением");
        }
        this.coordinates = coordinates;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        if (creationDate == null) {
            throw new IllegalArgumentException("Поле Время создания не может быть null-значением");
        }
        this.creationDate = creationDate;
    }

    public Long getStudentsCount() {
        return studentsCount;
    }

    public void setStudentsCount(Long studentsCount) {
        if (studentsCount != null && studentsCount <= 0) {
            throw new IllegalArgumentException("Поле Координаты не может быть негативным или null-значением");
        }
        this.studentsCount = studentsCount;
    }

    public Integer getShouldBeExpelled() {
        return shouldBeExpelled;
    }

    public void setShouldBeExpelled(Integer shouldBeExpelled) {
        if (shouldBeExpelled != null && shouldBeExpelled <= 0) {
            throw new IllegalArgumentException("Поле не может быть негативным или null-значением");
        }
        this.shouldBeExpelled = shouldBeExpelled;
    }

    public FormOfEducation getFormOfEducation() {
        return formOfEducation;
    }

    public void setFormOfEducation(FormOfEducation formOfEducation) {
        this.formOfEducation = formOfEducation;
    }

    public Semester getSemesterEnum() {
        return semesterEnum;
    }

    public void setSemesterEnum(Semester semesterEnum) {
        if (semesterEnum == null) {
            throw new IllegalArgumentException("Поле Семестр не может быть null-значением");
        }
        this.semesterEnum = semesterEnum;
    }

    public Person getGroupAdmin() {
        return groupAdmin;
    }

    public void setGroupAdmin(Person groupAdmin) {
        this.groupAdmin = groupAdmin;
    }

    //Сравнения для сортировки (по имени)

    @Override
    public int compareTo(StudyGroup other) {
        return this.name.compareTo(other.name);
    }

    //Проверка валидности объекта

    public boolean validate() {
        try {
            if (id == null || id <= 0) return false;
            if (name == null || name.trim().isEmpty()) return false;
            if (coordinates == null || !coordinates.validate()) return false;
            if (creationDate == null) return false;
            if (studentsCount != null && studentsCount <= 0) return false;
            if (shouldBeExpelled != null && shouldBeExpelled <= 0) return false;
            if (semesterEnum == null) return false;
            if (groupAdmin != null && !groupAdmin.validate()) return false;
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //Форматированное строковое представление объекта

    @Override
    public String toString() {
        return String.format(
                "StudyGroup [id=%d, name='%s', coordinates=%s, created=%s, students=%d, " +
                        "expelled=%d, form=%s, semester=%s, admin=%s]",
                id, name, coordinates, creationDate, studentsCount,
                shouldBeExpelled, formOfEducation, semesterEnum, groupAdmin
        );
    }
}