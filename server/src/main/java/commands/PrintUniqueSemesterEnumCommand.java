package commands;

import commandLine.Console;
import commandLine.Printable;
import exceptions.IllegalArguments;
import managers.CollectionManager;
import utility.Request;
import utility.Response;
import utility.ResponseStatus;

public class PrintUniqueSemesterEnumCommand extends Command{
    /**{@link CollectionManager}, в котором хранится коллекция и с помощью которого выполняется команда*/
    private final CollectionManager collectionManager;
    /**Поле, отвечающее за вывод информации о работе команды*/
    private final Printable console;
    public PrintUniqueSemesterEnumCommand(CollectionManager collectionManager, Printable console) {
        super("print_unique_semester_enum", "вывести уникальные значения поля semesterEnum всех элементов в коллекции");
        this.collectionManager = collectionManager;
        this.console = console;
    }

    /** Метод для выполнения команды
     * @param request Аргументы команды
     * @return ответ на выполнение команды
     * @throws IllegalArguments Поступили невалидные аргументы
     */
    @Override
    public Response execute(Request request) throws IllegalArguments {
        if (!request.getArgs().isBlank()) throw new IllegalArguments("В команде print_unique_semester_enum не должно быть аргументов");
        return new Response(ResponseStatus.OK, "Выводим уникальные значения поля Semester\n" + collectionManager.printUniqueSemester());
    }
}
