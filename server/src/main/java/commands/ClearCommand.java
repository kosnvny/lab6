package commands;

import commandLine.Console;
import commandLine.Printable;
import exceptions.*;
import managers.CollectionManager;
import utility.Request;
import utility.Response;
import utility.ResponseStatus;

public class ClearCommand extends Command{
    /**Поле, отвечающее за вывод информации о работе команды*/
    private final Printable console;
    /**{@link CollectionManager}, в котором хранится коллекция и с помощью которого выполняется команда*/
    private final CollectionManager collectionManager;
    public ClearCommand(CollectionManager collectionManager, Printable console) {
        super("clear", "очистить коллекцию");
        this.collectionManager = collectionManager;
        this.console = console;
    }
    /** Метод для выполнения команды
     * @param request Аргументы команды
     * @return ответ на выполнение команды
     * @throws IllegalArguments Выбрасывается, если получены не пустые аргументы
     * */
    @Override
    public Response execute(Request request) throws IllegalArguments {
        if (!request.getArgs().isBlank()) throw new IllegalArguments("В команде clear не должно быть аргументов");
        collectionManager.clearCollection();
        return new Response(ResponseStatus.OK, "Коллекция очищена");
    }
}
