package models;

//Класс, представляющий локацию

public class Location {
    private Integer x; // Поле не может быть null
    private Double y; // Поле не может быть null
    private long z;
    private String name; // Строка не может быть пустой, Поле может быть null

    public Location(Integer x, Double y, long z, String name) {
        setX(x);
        setY(y);
        setZ(z);
        setName(name);
    }

    //Проверяет правильность объекта Location

    public boolean validate() {
        try {
            if (x == null) return false;
            if (y == null) return false;
            if (name != null && name.trim().isEmpty()) return false;
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Геттеры и сеттеры
    public Integer getX() { return x; }
    public void setX(Integer x) {
        if (x == null) throw new IllegalArgumentException("X не может быть null");
        this.x = x;
    }


    public void setY(Double y) {
        if (y == null) throw new IllegalArgumentException("Y не может быть null");
        this.y = y;
    }

    public void setZ(long z) { this.z = z; }

    public String getName() { return name; }

    public void setName(String name) {
        if (name != null && name.trim().isEmpty()) {
            throw new IllegalArgumentException("Имя не может быть пустым.");
        }
        this.name = name;
    }

    @Override
    public String toString() {
        return "Location{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", name='" + name + '\'' +
                '}';
    }
}