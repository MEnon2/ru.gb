import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerChat {

    public static void main(String[] args) {
        new ServerChat();
    }

    public ServerChat() {
        createConnection();
    }

    private void createConnection() {
        Socket socket = null;
        try (ServerSocket serverSocket = new ServerSocket(8189)) {
            System.out.println("Сервер запущен и ожидает подключения");
            socket = serverSocket.accept();
            System.out.println("Соединение с клиентом установленно");

            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            Thread thread_read = new Thread(() -> {
                try {
                    while (true) {
                        String str = dis.readUTF();
                        if (str.equals("/end")) {
                            System.out.println("Пришла команда завершения соединения. Выключаем сервер.");
                            closeConnection(serverSocket, dis, dos);
                            break;
                        }
                        System.out.println("Клиент: " + str);
                    }
                } catch (IOException ioException) {
                    System.out.println("Произошло исключение на сервере при чтении из потока.");
                    closeConnection(serverSocket, dis, dos);
                    ioException.printStackTrace();
                }
            });
            thread_read.start();

            Thread thread_write = new Thread(() -> {
                try {
                    while (true) {
                        Scanner scanner = new Scanner(System.in);
                        String str = scanner.nextLine();
                        dos.writeUTF(str);
                        if (str.equals("/end")) {
                            System.out.println("Пришла команда завершения соединения. Выключаем сервер.");
                            closeConnection(serverSocket, dis, dos);
                            break;
                        }
                    }
                } catch (IOException ioException) {
                    System.out.println("Произошло исключение на сервере при записи в поток.");
                    closeConnection(serverSocket, dis, dos);
                    ioException.printStackTrace();
                }
            });
            thread_write.start();

        } catch (Exception e) {
            System.out.println("Произошло исключение на сервере");
            e.printStackTrace();
        }
    }

    private void closeConnection(ServerSocket serverSocket, DataInputStream dis, DataOutputStream dos) {
        try {
            dis.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            dos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            serverSocket.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("Соединение завершено");
    }
}
