package commands;

import commandLine.Console;
import commandLine.Printable;
import exceptions.IllegalArguments;
import managers.CommandManager;
import utility.Request;
import utility.Response;
import utility.ResponseStatus;

public class HelpCommand extends Command{
    /**Поле, отвечающее за вывод информации о работе команды*/
    private final Printable console;
    /**{@link CommandManager}, хранящий все команды*/
    private final CommandManager commandManager;
    public HelpCommand(CommandManager commandManager, Printable console) {
        super("help", "вывести справку по доступным командам");
        this.commandManager = commandManager;
        this.console = console;
    }
    /** Метод для выполнения команды
     * @param request Аргументы команды
     * @return ответ на выполнение команды
     * @throws IllegalArguments Были получены непустые аргументы
     */
    public Response execute(Request request) throws IllegalArguments {
        if (!request.getArgs().isBlank()) throw new IllegalArguments("В команде help нет аргументов");
        return new Response(ResponseStatus.OK, String.join("\n", commandManager.getAllCommands()));
    }
}
