package commands;

import commandLine.Console;
import commandLine.Printable;
import exceptions.IllegalArguments;
import exceptions.InvalideForm;
import managers.*;
import models.*;
import models.formsForUser.StudyGroupForm;
import utility.Request;
import utility.Response;
import utility.ResponseStatus;

import java.util.Objects;

/**
 * Класс команды add
 * */
public class AddCommand extends Command{
    /**{@link CollectionManager}, в котором хранится коллекция и с помощью которого выполняется команда*/
    private final CollectionManager collectionManager;
    /**Поле, отвечающее за вывод информации о работе команды*/
    private final Printable console;
    public AddCommand(CollectionManager collectionManager, Printable console) {
        super("add", "{element} : добавить новый элемент в коллекцию");
        this.collectionManager = collectionManager;
        this.console = console;
    }
    /**
     * Выполнение команды
     * @param request аргументы команды
     * @return ответ на выполнение команды
     * @throws IllegalArguments аргументы не валидны по различным причинам
     * @throws InvalideForm форма заполнения объектов {@link StudyGroup}, {@link Person} или их составляющих неправильна*/
    @Override
    public Response execute(Request request) throws IllegalArguments, InvalideForm {
        if (!request.getArgs().isBlank()) throw new IllegalArguments("Аргументы в команде add должны вводиться со следующей строки");
        if (Objects.isNull(request.getStudyGroup())) {
            return new Response(ResponseStatus.ASK_FOR_OBJECT, "Требуется объект для выполнения команды" + getName());
        } else {
            StudyGroup.updateID(collectionManager.getCollection());
            request.getStudyGroup().setId(StudyGroup.newID());
            collectionManager.addElement(request.getStudyGroup());
            return new Response(ResponseStatus.OK, "Объект был добавлен в коллекцию");
        }
    }
}
