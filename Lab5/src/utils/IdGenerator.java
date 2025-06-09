package utils;

public class IdGenerator {
    private long nextId = 1;

    public synchronized long generateId() {
        return nextId++;
    }

    public void setNextId(long id) {
        this.nextId = id;
    }
}