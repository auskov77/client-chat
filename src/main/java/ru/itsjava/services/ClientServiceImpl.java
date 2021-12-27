package ru.itsjava.services;

import lombok.SneakyThrows;

import java.io.PrintWriter;
import java.net.Socket;

public class ClientServiceImpl implements ClientService {
    public final static int PORT = 8081;
    public final static String HOST = "localhost";
    boolean isExit = false;

    @SneakyThrows
    @Override
    public void start() {
        Socket socket = new Socket(HOST, PORT);
        if (socket.isConnected()) {
            new Thread(new SocketRunnable(socket)).start();

            PrintWriter serverWriter = new PrintWriter(socket.getOutputStream());
            MessageInputService messageInputService =
                    new MessageInputServiceImpl(System.in);

            System.out.println("Введите свой логин:");
            String login = messageInputService.getMessage();

            System.out.println("Введите свой пароль:");
            String password = messageInputService.getMessage();

            // !autho!login:password
            serverWriter.println("!autho!" + login + ":" + password);
            serverWriter.flush();

            while (!isExit) {
//                System.out.println("Введите сообщение");
                String consoleMessage = messageInputService.getMessage();
                // 2. В клиенте сделать выход из цикла по слову "Exit"
                isExit = consoleMessage.equals("Exit");
                serverWriter.println(consoleMessage);
                serverWriter.flush();
                // проверка условия по слову "Exit" и если true то выход
                if (isExit) {
                    System.out.println("Доклад закончил, всем пока!");
                    System.exit(0);
                }
            }
        }
    }

}
