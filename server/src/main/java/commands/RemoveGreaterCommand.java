package commands;

import commandLine.Console;
import commandLine.Printable;
import exceptions.IllegalArguments;
import exceptions.InvalideForm;
import managers.CollectionManager;
import utility.Request;
import utility.Response;
import utility.ResponseStatus;


public class RemoveGreaterCommand extends Command {
    /**{@link CollectionManager}, в котором хранится коллекция и с помощью которого выполняется команда*/
    private final CollectionManager collectionManager;
    /**Поле, отвечающее за вывод информации о работе команды*/
    private final Printable console;
    public RemoveGreaterCommand(CollectionManager collectionManager, Printable console) {
        super("remove_greater", "{element} : удалить из коллекции все элементы, превышающие заданный");
        this.collectionManager = collectionManager;
        this.console = console;
    }

    /** Метод для выполнения команды
     * @param request Аргументы команды
     * @return ответ на выполнение команды
     * @throws IllegalArguments Поступили невалидные аргументы
     * @throws InvalideForm {@link StudyGroupForm} или {@link PersonForm} получила невалидные аргументы*/
    @Override
    public Response execute(Request request) throws IllegalArguments, InvalideForm {
        if (!request.getArgs().isBlank()) throw new IllegalArguments("В команде " + getName() + " аргументы вводятся с новой строки");
        collectionManager.removeGreater(new models.formsForUser.StudyGroupForm(console).build());
        return new Response(ResponseStatus.OK, "Удалили все элементы коллекции, превышающие заданного");
    }
}
