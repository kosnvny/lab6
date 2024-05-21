import commandLine.BlankConsole;
import commandLine.Printable;
import commands.*;
import exceptions.ForcedExit;
import managers.CollectionManager;
import managers.CommandManager;
import managers.FileManager;
import utility.RequestHandler;
import utility.ServerTCP;

import java.util.List;

public class AppServer {
    public static int port = 4567; // change!
    public static Printable console = new BlankConsole();
    public static void main(String[] args) {
        if (args.length != 0) {
            try {
                port = Integer.parseInt(args[0].trim());
            } catch (NumberFormatException e) {
                console.printError("вы ввели не число, поэтому будет использоваться порт по умолчанию");
            }
            CollectionManager collectionManager = new CollectionManager();
            FileManager fileManager = new FileManager(collectionManager, console);
            try {
                fileManager.readFile();
                fileManager.createObjects();
            } catch (ForcedExit e) {
                console.printError(e.getMessage());
                return;
            }
            CommandManager commandManager = new CommandManager(fileManager);
            commandManager.addCommands(List.of(new AddCommand(collectionManager, console),
                    new ClearCommand(collectionManager, console),
                    new CountLessThanSemesterEnumCommand(collectionManager, console),
                    new ExecuteScriptCommand(commandManager, console),
                    new ExitCommand(),
                    new HeadCommand(collectionManager, console),
                    new HelpCommand(commandManager, console),
                    new InfoCommand(collectionManager, console),
                    new PrintDescendingCommand(collectionManager, console),
                    new PrintUniqueSemesterEnumCommand(collectionManager, console),
                    new RemoveByIDCommand(collectionManager, console),
                    new RemoveFirstCommand(collectionManager),
                    new RemoveGreaterCommand(collectionManager, console),
                    new ShowCommand(collectionManager, console),
                    new UpdateIDCommand(collectionManager, console)));
            RequestHandler requestHandler = new RequestHandler(commandManager);
            ServerTCP serverTCP = new ServerTCP(port, console, requestHandler, fileManager);
            serverTCP.run();
        }
    }
}
