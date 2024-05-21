import commandLine.Console;
import commandLine.Printable;
import exceptions.IllegalArguments;
import utility.ClientTCP;
import utility.RuntimeManager;

public class AppClient {
    private static String host;
    private static int port;
    private static Printable console;

    public static void main(String[] args) {
        if (!getHostAndPort(args)) return;
        console = new Console();
        ClientTCP clientTCP = new ClientTCP(host, port, 3, 1000, console);
        new RuntimeManager(console, clientTCP).letsGo();
    }

    private static boolean getHostAndPort(String[] args) {
        try {
            if (args.length != 2) throw new IllegalArguments("Введите, пожалуйста, хост и порт");
            host = args[0];
            port = Integer.parseInt(args[1]);
            if (port < 0) throw new IllegalArguments("Значение порта должно быть положительным целым числом");
            return true;
        } catch (IllegalArguments e) {
            console.printError(e.getMessage());
        }
        return false;
    }
}
