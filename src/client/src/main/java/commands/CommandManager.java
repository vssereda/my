package client.src.main.java.commands;

import utils.CollectionManager;
import utils.FileManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class CommandManager {
    private final Map<String, Command> commands = new HashMap<>();
    private final CollectionManager collectionManager;
    private final FileManager fileManager;
    private final String filename;
    private final Stack<String> scriptStack = new Stack<>(); // штука для отслеживания выполнения скриптов

    public CommandManager(CollectionManager collectionManager,
                          FileManager fileManager,
                          String filename) {
        this.collectionManager = collectionManager;
        this.fileManager = fileManager;
        this.filename = filename;
        registerCommands();
    }

    private void registerCommands() {
        registerCommand(new HelpCommand(this.getCommands()));
        registerCommand(new InfoCommand(collectionManager));
        registerCommand(new ShowCommand(collectionManager));
        registerCommand(new InsertCommand(collectionManager));
        registerCommand(new UpdateCommand(collectionManager));
        registerCommand(new RemoveKeyCommand(collectionManager));
        registerCommand(new ClearCommand(collectionManager));
        registerCommand(new SaveCommand(collectionManager, fileManager, filename));
        registerCommand(new ExecuteScriptCommand(this));
        registerCommand(new ExitCommand());
        registerCommand(new RemoveGreaterCommand(collectionManager));
        registerCommand(new ReplaceIfGreaterCommand(collectionManager));
        registerCommand(new ReplaceIfLowerCommand(collectionManager));
        registerCommand(new FilterLessThanSemestErenumCommand(collectionManager));
        registerCommand(new FilterGreaterThanStudentsCountCommand(collectionManager));
        registerCommand(new PrintFieldDescendingFormOfEducationCommand(collectionManager));
    }

    private void registerCommand(Command command) {
        commands.put(command.getName(), command);
    }

    public void executeCommand(String commandName, String[] args) {
        Command cmd = commands.get(commandName);
        if (cmd == null) {
            System.out.println("Команда '" + commandName + "' не найдена. Введите 'help' для списка команд.");
            return;
        }

        try {
            cmd.execute(args);
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка в аргументах: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Ошибка при выполнении команды: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Метод для работы со скриптами
    public void pushScript(String filename) {
        if (scriptStack.contains(filename)) {
            throw new IllegalArgumentException("Обнаружена рекурсия в скрипте: " + filename);
        }
        scriptStack.push(filename);
    }

    // Проверяет, есть ли файл в стеке выполнения
    public boolean isScriptInStack(String filename) {
        return scriptStack.contains(filename);
    }

    // Возвращает текущий выполняемый скрипт
    public String getCurrentScript() {
        return scriptStack.isEmpty() ? null : scriptStack.peek();
    }


    public void popScript() {
        scriptStack.pop();
    }

    public boolean isExecutingScript() {
        return !scriptStack.isEmpty();
    }

    public Map<String, Command> getCommands() {
        return commands;
    }
}