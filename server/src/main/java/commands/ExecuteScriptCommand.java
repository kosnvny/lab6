package commands;

import commandLine.ExecuteScriptManager;
import commandLine.Printable;
import exceptions.*;
import managers.CommandManager;
import managers.FileManager;
import utility.Request;
import utility.Response;
import utility.ResponseStatus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Класс команды execute_script*/
public class ExecuteScriptCommand extends Command{
    /**Поле, отвечающее за вывод информации о работе команды*/
    private final Printable console;
    /**{@link CommandManager}, запускающий выполнение команд*/
    private final CommandManager commandManager;
    /**{@link FileManager}, работающий с поступившем файлом*/
    public ExecuteScriptCommand(CommandManager commandManager, Printable console) {
        super("execute_script", "file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.");
        this.commandManager = commandManager;
        this.console = console;
    }
    /** Метод для выполнения команды
     * @param request Аргументы команды
     * @return ответ на выполнение команды
     * @throws IllegalArguments Аргумент этой команды не может быть пустым
     * @throws RecursionInScriptException Скрипт вызывает сам себя, или скрипты образовали цикл
     * @throws ForcedExit Во время выполнения команды случилось непоправимое
     * @throws CommandDoesNotExist Команда не существует
     * @throws InvalideForm {@link models.formsForUser.StudyGroupForm} или {@link models.formsForUser.PersonForm} получила неверные аргументы*/
    @Override
    public Response execute(Request request) throws IllegalArguments, RecursionInScriptException, ForcedExit, CommandDoesNotExist, InvalideForm {
        if (request.getArgs().isBlank()) throw new IllegalArguments("В команде execute_script аргументом должен быть путь");
        return new Response(ResponseStatus.EXECUTE_SCRIPT, request.getArgs());
    }
}
