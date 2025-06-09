import models.StudyGroup;

import java.io.Serializable;

public class Command implements Serializable {
    private String name;
    private Object[] args;
    private StudyGroup studyGroup; // Для команд, требующих StudyGroup

    public Command(String name, Integer args) {
        this.name = name;
        this.args = args;
    }
}