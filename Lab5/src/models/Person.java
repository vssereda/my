package models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class Person implements Serializable, Comparable<Person> {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;
    private int height;
    private Color hairColor;
    private Country nationality;
    private Location location;
    private LocalDateTime creationDate;

    public Person(String name, int height, Color hairColor,
                  Country nationality, Location location) {
        this(null, name, height, hairColor, nationality, location, null);
    }

    public Person(Integer id, String name, int height, Color hairColor,
                  Country nationality, Location location, LocalDateTime creationDate) {
        setName(name);
        setHeight(height);
        setHairColor(hairColor);
        setNationality(nationality);
        setLocation(location);
        this.id = id;
        this.creationDate = creationDate != null ? creationDate : LocalDateTime.now();
    }

    public Person() {

    }

    public static Person fromArgs(String[] args) {
        if (args.length < 5) {
            throw new IllegalArgumentException("Недостаточно аргументов");
        }

        return new Person(
                args[0], // name
                Integer.parseInt(args[1]), // height
                Color.valueOf(args[2].toUpperCase()), // hairColor
                args[3].equals("null") ? null : Country.valueOf(args[3].toUpperCase()), // nationality
                args[4].equals("null") ? null : Location.fromString(args[4]) // location
        );
    }

    @Override
    public int compareTo(Person other) {
        if (this.location == null && other.location == null) return 0;
        if (this.location == null) return -1;
        if (other.location == null) return 1;
        return this.location.compareTo(other.location);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return height == person.height &&
                Objects.equals(id, person.id) &&
                Objects.equals(name, person.name) &&
                hairColor == person.hairColor &&
                nationality == person.nationality &&
                Objects.equals(location, person.location) &&
                Objects.equals(creationDate, person.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, height, hairColor, nationality, location, creationDate);
    }

    // Геттеры и сеттеры с валидацией
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        if (id != null && id <= 0) {
            throw new IllegalArgumentException("ID должен быть больше 0");
        }
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Имя не может быть null или пустым");
        }
        this.name = name;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        if (height <= 0) {
            throw new IllegalArgumentException("Рост должен быть больше 0");
        }
        this.height = height;
    }

    public Color getHairColor() {
        return hairColor;
    }

    public void setHairColor(Color hairColor) {
        if (hairColor == null) {
            throw new IllegalArgumentException("Цвет волос не может быть null");
        }
        this.hairColor = hairColor;
    }

    public Country getNationality() {
        return nationality;
    }

    public void setNationality(Country nationality) {
        this.nationality = nationality;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return String.format(
                "Person{id=%s, name='%s', height=%d, hairColor=%s, nationality=%s, location=%s, created=%s}",
                id, name, height, hairColor, nationality, location, creationDate
        );
    }

    public boolean validate() {
        return false;
    }

    public void setCoordinates(Coordinates coordinates) {
    }
}