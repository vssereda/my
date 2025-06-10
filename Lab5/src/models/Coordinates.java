package models;

//Класс, представляющий координаты.

public class Coordinates {
    private Integer x; // Максимальное значение: 394, не может быть null
    private long y;

    //Основной конструктор
    //IllegalArgumentException если параметры невалидны

    public Coordinates(Integer x, long y) {
        setX(x);
        setY(y);
    }

    //Проверяет валидность объекта
    public boolean validate() {
        return x != null && x <= 394;
    }

    // Геттеры и сеттеры с валидацией

    public Integer getX() {
        return x;
    }

    //Устанавливает координату X
    //IllegalArgumentException если значение невалидно

    public void setX(Integer x) {
        if (x == null) {
            throw new IllegalArgumentException("Координата X не может быть null");
        }
        if (x > 394) {
            throw new IllegalArgumentException("Координата X не может быть больше 394");
        }
        this.x = x;
    }

    public long getY() {
        return y;
    }

    public void setY(long y) {
        this.y = y;
    }

    //Строковое представление объекта, возвращает строку

    @Override
    public String toString() {
        return String.format("Coordinates{x=%d, y=%d}", x, y);
    }

    //Сравнение объектов
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Coordinates that = (Coordinates) obj;
        return y == that.y && x.equals(that.x);
    }

    //Генерация хеш-кода

    @Override
    public int hashCode() {
        return 31 * x + (int) (y ^ (y >>> 32));
    }
}