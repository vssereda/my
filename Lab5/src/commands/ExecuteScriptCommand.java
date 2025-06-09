package commands;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ExecuteScriptCommand implements Command {
    private final CommandManager commandManager;

    public ExecuteScriptCommand(String commandManager) {
        this.commandManager = commandManager;
    }

    @Override
    public void execute(String[] args) {
        String filename;

        // Запрашиваем имя файла у пользователя
        if (args.length < 1) {
            System.out.print("Введите имя файла скрипта: ");
            Scanner consoleScanner = new Scanner(System.in);
            filename = consoleScanner.nextLine().trim();
        } else {
            filename = args[0];
        }

        // Проверка на рекурсивный вызов
        if (commandManager.isScriptInStack(filename)) {
            System.out.println("Ошибка: обнаружена рекурсия! Файл '" + filename + "' уже выполняется");
            return;
        }

        // Поиск файла в директории программы
        File scriptFile = new File(System.getProperty("user.dir"), filename);

        try {
            commandManager.pushScript(filename);
            executeScript(scriptFile);
        } catch (FileNotFoundException e) {
            System.out.println("Ошибка: файл скрипта не найден: " + scriptFile.getAbsolutePath());
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        } finally {
            if (commandManager.isExecutingScript() && filename.equals(commandManager.getCurrentScript())) {
                commandManager.popScript();
            }
        }
    }

    private void executeScript(File scriptFile) throws FileNotFoundException {
        try (Scanner scanner = new Scanner(scriptFile)) {
            int lineNumber = 0;
            while (scanner.hasNextLine()) {
                lineNumber++;
                String line = scanner.nextLine().trim();

                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                System.out.printf("[%s:%d] >> %s%n", scriptFile.getName(), lineNumber, line);

                String[] parts = line.split("\\s+");
                String commandName = parts[0];
                String[] commandArgs = new String[parts.length - 1];
                System.arraycopy(parts, 1, commandArgs, 0, commandArgs.length);

                commandManager.executeCommand(commandName, commandArgs);
            }
        }
    }

    @Override
    public String getName() {
        return "execute_script";
    }

    @Override
    public String getDescription() {
        return "считать и исполнить скрипт из указанного файла (имя можно ввести при запросе)";
    }
}