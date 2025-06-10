package commands;


public interface Command {

    void execute(String[] args);

    String getName();

    String getDescription();

    default String getDetailedDescription() {
        return getDescription();
    }

    default String getUsage() {
        return getName();
    }
}