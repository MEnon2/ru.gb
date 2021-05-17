package lesson8;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerChat {

    private ServerSocket serverSocket;
    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;


    public static void main(String[] args) {
        new ServerChat();
    }

    public ServerChat() {

        try {
            this.serverSocket = new ServerSocket(8189);
            System.out.println("Сервер запущен и ожидает подключения");
            Thread stream1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        socket = serverSocket.accept();
                        System.out.println("Соединение с клиентом установленно");

                        //mainChat.setText("test");

                        dis = new DataInputStream(socket.getInputStream());
                        dos = new DataOutputStream(socket.getOutputStream());

                        Thread thread_read = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    while (true) {
                                        String str = dis.readUTF();
                                        if (str.equals("/end")) {
                                            System.out.println("Пришла команда завершения соединения. Выключаем сервер.");
                                            closeConnection();
                                            break;
                                        }
                                        System.out.println("Клиент: " + str);
                                    }
                                } catch (IOException ioException) {
                                    System.out.println("Произошло исключение на сервере при чтении из потока.");
                                    closeConnection();
                                    ioException.printStackTrace();
                                }
                            }
                        });
                        thread_read.start();

                        Thread thread_write = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    while (true) {
                                        Scanner scanner = new Scanner(System.in);
                                        String str = scanner.nextLine();
                                        dos.writeUTF(str);
                                        if (str.equals("/end")) {
                                            System.out.println("Пришла команда завершения соединения. Выключаем сервер.");
                                            closeConnection();
                                            break;
                                        }
                                    }
                                } catch (IOException ioException) {
                                    System.out.println("Произошло исключение на сервере при записи в поток.");
                                    closeConnection();
                                    ioException.printStackTrace();
                                }
                            }
                        });
                        thread_write.start();

                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            });
            stream1.start();
        } catch (IOException ioException) {
            System.out.println("Произошло исключение на сервере");
            ioException.printStackTrace();
        }

    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public Socket getSocket() {
        return socket;
    }

    public DataInputStream getDis() {
        return dis;
    }

    public DataOutputStream getDos() {
        return dos;
    }

    public void closeConnection() {
        try {
            this.dis.close();
            System.out.println("dis close");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            this.dos.close();
            System.out.println("dos close");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            this.serverSocket.close();
            System.out.println("serverSocket close");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
