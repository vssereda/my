package commands;

import java.io.Serializable;

public class CommandPackage implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String commandName;
    private final Object[] args;

    public CommandPackage(String commandName, Object[] args) {
        this.commandName = commandName;
        this.args = args;
    }

    public String getCommandName() {
        return commandName;
    }

    public Object[] getArgs() {
        return args;
    }
}