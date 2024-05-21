package utility;

import commandLine.Printable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Objects;

public class ClientTCP {
    private final String host;
    private final int port;
    private final Printable console;
    private Socket socket;
    private int countOfReconnections;
    private final int maxCountOfConnections;
    private final int reconnectionTimeout;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    public ClientTCP(String host, int port, int maxCountOfConnections, int reconnectionTimeout, Printable console) {
        this.host = host;
        this.port = port;
        this.maxCountOfConnections = maxCountOfConnections;
        this.reconnectionTimeout = reconnectionTimeout;
        this.console = console;
    }

    public void connectToServer() {
        try {
            if (countOfReconnections > 0) console.println("Повторим подключение!");
            this.socket = new Socket(host, port);
            this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        } catch (IllegalArgumentException e) {
            console.printError("адрес сервера введён некорректно(");
        } catch (IOException e) {
            console.printError("ошибка при соединении с сервером");
        }
    }

    public void disconnectFromServer() {
        try {
            this.socket.close();
            objectOutputStream.close();
            objectInputStream.close();
        } catch (IOException e) {
            console.printError("нет подключения к серверу");
        }
    }

    public Response sendAndAskResponse(Request request) {
        while (true) {
            try {
                if (Objects.isNull(objectOutputStream)) throw new IOException();
                if (request.isEmpty()) return new Response(ResponseStatus.ERROR, "Запрос пуст!");
                objectOutputStream.writeObject(request);
                objectOutputStream.flush();
                this.objectInputStream = new ObjectInputStream(socket.getInputStream());
                Response response = (Response) objectInputStream.readObject();
                this.disconnectFromServer();
                countOfReconnections = 0;
                return response;
            } catch (IOException e) {
                if (countOfReconnections == 0) {
                    console.println("Попробуем подключиться снова!");
                    connectToServer();
                    countOfReconnections++;
                    continue;
                } else {
                    console.printError("Соединение с сервером разорвано(");
                }
                try {
                    countOfReconnections++;
                    if (countOfReconnections >= maxCountOfConnections) {
                        console.printError("Превышено максимальное количество переподключений: " + maxCountOfConnections);
                        return new Response(ResponseStatus.ERROR);
                    }
                    console.println("Повторно подключимся через " + reconnectionTimeout / 1000 + " секунд.");
                    Thread.sleep(reconnectionTimeout);
                    connectToServer();
                } catch (Exception ex) {
                    console.printError("Попытка соединения с сервером неуспешна(");
                }
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
