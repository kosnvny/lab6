package commands;

import commandLine.Console;
import commandLine.Printable;
import exceptions.IllegalArguments;
import managers.CollectionManager;
import utility.Request;
import utility.Response;
import utility.ResponseStatus;

public class HeadCommand extends Command{
    /**Поле, отвечающее за вывод информации о работе команды*/
    private final Printable console;
    /**{@link CollectionManager}, в котором хранится коллекция и с помощью которого выполняется команда*/
    private final CollectionManager collectionManager;
    public HeadCommand(CollectionManager collectionManager, Printable console) {
        super("head", "вывести первый элемент коллекции");
        this.collectionManager = collectionManager;
        this.console = console;
    }
    /** Метод для выполнения команды
     * @param request Аргументы команды
     * @return ответ на выполнение команды
     * @throws IllegalArguments Были получены аргументы
     */
    @Override
    public Response execute(Request request) throws IllegalArguments {
        if (!request.getArgs().isBlank()) throw new IllegalArguments("В команде head не должно быть аргументов");
        if (collectionManager.getCollection().isEmpty()) throw new IllegalArguments("В коллекции нет элементов!");
        return new Response(ResponseStatus.OK, "Первый элемент коллекции: " + collectionManager.head().toString());
    }
}
