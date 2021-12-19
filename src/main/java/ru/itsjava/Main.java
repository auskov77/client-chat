package ru.itsjava;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Main {
    public final static int PORT = 8081;
    public final static String HOST = "localhost";

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket(HOST, PORT);

        if (socket.isConnected()){
            PrintWriter serverWriter = new PrintWriter(socket.getOutputStream());
            serverWriter.println("Hi from client!");
            serverWriter.flush();
        }
    }
}
