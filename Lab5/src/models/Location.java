package models;

import java.io.Serializable;
import java.util.Objects;

/**
 * Класс, представляющий локацию с координатами (x, y, z) и названием
 */
public class Location implements Serializable, Comparable<Location> {
    private static final long serialVersionUID = 1L;

    private Integer x;     // Поле не может быть null
    private Double y;      // Поле не может быть null
    private long z;
    private String name;   // Поле может быть null, но если не null - не может быть пустым

    public Location(Integer x, Double y, long z, String name) {
        setX(x);
        setY(y);
        setZ(z);
        setName(name);
    }

    public Location() {

    }

    /**
     * Проверяет валидность объекта Location
     * @return true если объект валиден, false в противном случае
     */
    public boolean validate() {
        return x != null &&
                y != null &&
                (name == null || !name.trim().isEmpty());
    }

    // Геттеры и сеттеры с валидацией

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        if (x == null) {
            throw new IllegalArgumentException("Координата X не может быть null");
        }
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        if (y == null) {
            throw new IllegalArgumentException("Координата Y не может быть null");
        }
        this.y = y;
    }

    public long getZ() {
        return z;
    }

    public void setZ(long z) {
        this.z = z;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null && name.trim().isEmpty()) {
            throw new IllegalArgumentException("Название локации не может быть пустой строкой");
        }
        this.name = name;
    }

    /**
     * Сравнение локаций по названию (для сортировки)
     */
    @Override
    public int compareTo(Location other) {
        if (other == null) return 1;
        if (this.name == null && other.name == null) return 0;
        if (this.name == null) return -1;
        if (other.name == null) return 1;
        return this.name.compareTo(other.name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return z == location.z &&
                Objects.equals(x, location.x) &&
                Objects.equals(y, location.y) &&
                Objects.equals(name, location.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z, name);
    }

    /**
     * Создает Location из строки формата "x,y,z,name"
     */
    public static Location fromString(String str) {
        if (str == null || str.trim().isEmpty()) {
            return null;
        }

        String[] parts = str.split(",");
        if (parts.length != 4) {
            throw new IllegalArgumentException("Неверный формат локации. Ожидается: x,y,z,name");
        }

        try {
            return new Location(
                    Integer.parseInt(parts[0].trim()),
                    Double.parseDouble(parts[1].trim()),
                    Long.parseLong(parts[2].trim()),
                    parts[3].trim().isEmpty() ? null : parts[3].trim()
            );
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Неверный формат координат", e);
        }
    }

    @Override
    public String toString() {
        return String.format(
                "Location{x=%d, y=%.2f, z=%d, name='%s'}",
                x, y, z, name != null ? name : "null"
        );
    }
}