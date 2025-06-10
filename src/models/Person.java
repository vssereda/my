package models;


public class Person {
    private String name; // Поле не может быть null, строка не может быть пустой
    private int height; // Значение поля должно быть больше 0
    private Color hairColor; // Поле не может быть null
    private Country nationality; // Поле может быть null
    private Location location; // Поле может быть null

    public Person(String name, int height, Color hairColor,
                  Country nationality, Location location) {
        setName(name);
        setHeight(height);
        setHairColor(hairColor);
        setNationality(nationality);
        setLocation(location);
    }


    //Проверяет валидность объекта Person

    public boolean validate() {
        try {
            if (name == null || name.trim().isEmpty()) return false;
            if (height <= 0) return false;
            if (hairColor == null) return false;
            if (location != null && !location.validate()) return false;
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Имя не может быть null или пустым.");
        }
        this.name = name;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        if (height <= 0) {
            throw new IllegalArgumentException("Рост должен быть больше 0.");
        }
        this.height = height;
    }

    public Color getHairColor() {
        return hairColor;
    }

    public void setHairColor(Color hairColor) {
        if (hairColor == null) {
            throw new IllegalArgumentException("Цвет волос не может быть null.");
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

    //Форматированное строковое представление объекта,
    // возвращает его строковое представление

    @Override
    public String toString() {
        return String.format(
                "Person{name='%s', height=%d, hairColor=%s, nationality=%s, location=%s}",
                name, height, hairColor, nationality, location
        );
    }
}