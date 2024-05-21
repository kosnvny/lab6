package utility;

import exceptions.*;
import managers.CommandManager;

public class RequestHandler {
    private CommandManager commandManager;
    public RequestHandler(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    public Response handle(Request request) {
        try {
            return commandManager.execute(request);
        } catch (CommandDoesNotExist e) {
            return new Response(ResponseStatus.ERROR, "Команды не существует");
        } catch (IllegalArguments e) {
            return new Response(ResponseStatus.WRONG_ARGUMENTS, "Невалидные аргументы для команды");
        } catch (ForcedExit e) {
            return new Response(ResponseStatus.EXIT);
        } catch (RecursionInScriptException e) {
            return new Response(ResponseStatus.ERROR, "Рекурсия в запускаемых файлах");
        } catch (InvalideForm e) {
            return new Response(ResponseStatus.WRONG_ARGUMENTS, "Форма создания объекта получила невалидные значения");
        }
    }
}
