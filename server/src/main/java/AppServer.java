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
                console.println("вы ввели не число, поэтому будет использоваться порт по умолчанию");
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
            commandManager.addCommands(List.of(new AddCommand(collectionManager),
                    new ClearCommand(collectionManager),
                    new CountLessThanSemesterEnumCommand(collectionManager),
                    new ExecuteScriptCommand(commandManager),
                    new ExitCommand(),
                    new HeadCommand(collectionManager),
                    new HelpCommand(commandManager),
                    new InfoCommand(collectionManager),
                    new PrintDescendingCommand(collectionManager),
                    new PrintUniqueSemesterEnumCommand(collectionManager),
                    new RemoveByIDCommand(collectionManager),
                    new RemoveFirstCommand(collectionManager),
                    new RemoveGreaterCommand(collectionManager),
                    new ShowCommand(collectionManager),
                    new UpdateIDCommand(collectionManager)));
            RequestHandler requestHandler = new RequestHandler(commandManager);
            ServerTCP serverTCP = new ServerTCP(port, console, requestHandler, fileManager);
            serverTCP.run();
        }
    }
}
