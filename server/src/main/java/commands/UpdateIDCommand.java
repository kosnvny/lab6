package commands;

import commandLine.Console;
import commandLine.Printable;
import exceptions.*;
import managers.CollectionManager;
import models.StudyGroup;
import models.formsForUser.StudyGroupForm;
import utility.Request;
import utility.Response;
import utility.ResponseStatus;

import java.util.Objects;


public class UpdateIDCommand extends Command{
    /**{@link CollectionManager}, в котором хранится коллекция и с помощью которого выполняется команда*/
    private final CollectionManager collectionManager;
    /**Поле, отвечающее за вывод информации о работе команды*/
    private final Printable console;
    public UpdateIDCommand(CollectionManager collectionManager, Printable console) {
        super("update", "id {element} : обновить значение элемента коллекции, id которого равен заданному");
        this.collectionManager = collectionManager;
        this.console = console;
    }

    /** Метод для выполнения команды
     * @param request Аргументы команды
     * @return ответ на выполнение команды
     * @throws IllegalArguments Аргумент этой команды не может быть пустым
     * @throws InvalideForm Форма для {@link StudyGroup} получила некорректные аргументы*/
    @Override
    public Response execute(Request request) throws IllegalArguments, InvalideForm {
        if (request.getArgs().isBlank()) throw new IllegalArguments("В команде update должны быть непустые аргументы");
        try {
            int id = Integer.parseInt(request.getArgs().trim());
            if (collectionManager.checkIfExists(id)) {
                if (Objects.isNull(request.getStudyGroup())) {
                    return new Response(ResponseStatus.ASK_FOR_OBJECT, "");
                }
                collectionManager.updateID(id, new StudyGroupForm(console).build());
                return new Response(ResponseStatus.OK, "Элемент коллекции с заданным id был обновлён");
            } else {
                return new Response(ResponseStatus.ERROR, "Данный id не существует");
            }
        } catch (NumberFormatException e) {
            return new Response(ResponseStatus.ERROR, "id должен быть целочисленным");
        }
    }
}
