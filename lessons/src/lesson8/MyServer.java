package lesson8;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MyServer {

    private List<ClientHandler> clients;
    private AuthService authService;

    public AuthService getAuthService() {
        return authService;
    }

    public MyServer() {
        try (ServerSocket serverSocket = new ServerSocket(ChatConstants.PORT)){
            System.out.println("Сервер запущен");
            authService = new BaseAuthService();
            authService.start();

            clients = new ArrayList<>();
            while (true) {
                System.out.println("Сервер ожидает подключение");
                Socket socket = serverSocket.accept();
                System.out.println("Соединение с клиентом установленно");
                new ClientHandler(this, socket);
            }


        }catch (IOException ex) {

        }
    }

}
