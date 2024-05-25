import commandLine.Console;
import commandLine.Printable;
import utility.ClientTCP;
import utility.RuntimeManager;

public class AppClient {
    private static Printable console = new Console();

    public static void main(String[] args) {
        String host = "localhost";
        int port = 4567;
        ClientTCP clientTCP = new ClientTCP(host, port, 3, 5000, console);
        new RuntimeManager(console, clientTCP).letsGo();
    }
}
