package commands;

import common.commands.*;
import common.model.Person;
import utils.CollectionManager;
import utils.FileManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Менеджер команд для обработки и выполнения клиентских команд
 * Теперь работает на серверной стороне
 */
public class CommandManager {
    private final Map<String, Command> commands = new HashMap<>();
    private final CollectionManager collectionManager;
    private final FileManager fileManager;
    private final String filename;
    private final Stack<String> scriptStack = new Stack<>();

    public CommandManager(CollectionManager collectionManager,
                          FileManager fileManager,
                          String filename) {
        this.collectionManager = collectionManager;
        this.fileManager = fileManager;
        this.filename = filename;
        registerCommands();
    }

    /**
     * Регистрация всех доступных команд
     */
    private void registerCommands() {
        // Базовые команды
        registerCommand(new HelpCommand());
        registerCommand(new InfoCommand());
        registerCommand(new ShowCommand());
        registerCommand(new ClearCommand(collectionManager));

        // Команды модификации коллекции
        registerCommand(new InsertCommand(collectionManager));
        registerCommand(new UpdateCommand(collectionManager));
        registerCommand(new RemoveKeyCommand(collectionManager));
        registerCommand(new RemoveGreaterCommand(collectionManager));

        // Команды сравнения
        registerCommand(new ReplaceIfGreaterCommand(collectionManager, readStudyGroupFromConsole()));
        registerCommand(new ReplaceIfLowerCommand(collectionManager, readStudyGroupFromConsole()));

        // Команды фильтрации
        registerCommand(new FilterLessThanSemestErenumCommand(collectionManager));
        registerCommand(new FilterGreaterThanStudentsCountCommand(collectionManager));

        // Специальные команды
        registerCommand(new PrintFieldDescendingFormOfEducationCommand(collectionManager));

        // Серверные команды (недоступные клиенту)
        registerCommand(new SaveCommand(collectionManager, fileManager, filename));
        registerCommand(new ExitCommand());
    }

    private void registerCommand(Command command) {
        commands.put(command.getName().toLowerCase(), command);
    }

    /**
     * Выполнение команды с обработкой ошибок
     */
    public CommandResult executeCommand(String commandName, Object[] args) {
        Command cmd = commands.get(commandName.toLowerCase());
        if (cmd == null) {
            return new CommandResult(false, "Команда '" + commandName + "' не найдена. Введите 'help' для списка команд.");
        }

        try {
            // Проверка прав доступа для серверных команд
            if (cmd instanceof ServerCommand && !(args.length > 0 && args[0] instanceof ServerCommandToken)) {
                return new CommandResult(false, "Недостаточно прав для выполнения этой команды");
            }

            return cmd.execute(args);
        } catch (IllegalArgumentException e) {
            return new CommandResult(false, "Ошибка в аргументах: " + e.getMessage());
        } catch (Exception e) {
            return new CommandResult(false, "Ошибка при выполнении команды: " + e.getMessage());
        }
    }

    /**
     * Методы для работы со скриптами (теперь выполняются на сервере)
     */
    public void pushScript(String filename) {
        if (scriptStack.contains(filename)) {
            throw new IllegalArgumentException("Обнаружена рекурсия в скрипте: " + filename);
        }
        scriptStack.push(filename);
    }

    public boolean isScriptInStack(String filename) {
        return scriptStack.contains(filename);
    }

    public String getCurrentScript() {
        return scriptStack.isEmpty() ? null : scriptStack.peek();
    }

    public void popScript() {
        scriptStack.pop();
    }

    public boolean isExecutingScript() {
        return !scriptStack.isEmpty();
    }

    /**
     * Возвращает копию карты команд (для безопасности)
     */
    public Map<String, Command> getCommandsMap() {
        return new HashMap<>(commands);
    }

    /**
     * Внутренний класс для передачи результата выполнения команды
     */
    public static class CommandResult implements java.io.Serializable {
        private final boolean success;
        private final String message;

        public CommandResult(boolean success, String message) {
            this.success = success;
            this.message = message;
        }

        public boolean isSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }

        @Override
        public String toString() {
            return message;
        }
    }

    /**
     * Маркерный интерфейс для серверных команд
     */
    public interface ServerCommand {
    }

    /**
     * Токен доступа для серверных команд
     */
    public static class ServerCommandToken implements java.io.Serializable {
        private final String token;

        public ServerCommandToken(String token) {
            this.token = token;
        }

        public boolean isValid() {
            // Здесь должна быть реальная проверка токена
            return token != null && token.equals("secure_token_123");
        }
    }
}