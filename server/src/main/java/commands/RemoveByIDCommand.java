package commands;

import commandLine.Console;
import commandLine.Printable;
import exceptions.IllegalArguments;
import managers.CollectionManager;
import utility.Request;
import utility.Response;
import utility.ResponseStatus;

public class RemoveByIDCommand extends Command {
    /**{@link CollectionManager}, в котором хранится коллекция и с помощью которого выполняется команда*/
    private final CollectionManager collectionManager;
    /**Поле, отвечающее за вывод информации о работе команды*/
    private final Printable console;
    public RemoveByIDCommand(CollectionManager collectionManager, Printable console) {
        super("remove_by_id", "id : удалить элемент из коллекции по его id");
        this.collectionManager = collectionManager;
        this.console = console;
    }
    /** Метод для выполнения команды
     * @param request Аргументы команды
     * @return ответ на выполнение команды
     * @throws IllegalArguments Команде поступили невалидные аргументы
     */
    @Override
    public Response execute(Request request) throws IllegalArguments {
        if (request.getArgs().isBlank()) throw new IllegalArguments("В команде remove_by_id должны быть аргументы");
        try {
            int id = Integer.parseInt(request.getArgs().trim());
            collectionManager.removeByID(id);
            return new Response(ResponseStatus.OK, "Элемент по заданному id был удалён");
        } catch (NumberFormatException e) {
            return new Response(ResponseStatus.ERROR, "Вы ввели не целое число(");
        }
    }
}
