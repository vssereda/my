package server;

import commands.Command;
import commands.*;
import models.Person;

import java.util.LinkedList;

public class CommandProcessor {
    public Object process(Object request) {
        if (request instanceof Command) {
            Command command = (Command) request;
            return command;
        }
        return "Unknown command type";
    }
}