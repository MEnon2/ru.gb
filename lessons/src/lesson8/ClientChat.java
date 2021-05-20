package lesson8;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientChat extends Application {

    private static final String SERVER_ADDR = "localhost";
    private static final int SERVER_PORT = 8189;
    private Socket socket = null;
    private DataInputStream dis;
    private DataOutputStream dos;


    @FXML
    public TextField messageField;
    @FXML
    public TextField loginField;
    @FXML
    public TextField passField;

    @FXML
    public TextArea mainChat;

    public static void main(String[] args) {

        launch(args);
       // new ClientChat();
    }

    public ClientChat() {

       // createConnection();

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("chat_structure.fxml"));
        primaryStage.setTitle("Чат клиент");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.show();

    }

    public void createConnection() {


        try {
            // Socket socket = new Socket(SERVER_ADDR, SERVER_PORT);
            socket = new Socket(SERVER_ADDR, SERVER_PORT);
            System.out.println("socket connected123");
            //String sss = mainChat.getText();
//            System.out.println(mainChat.getText().toString());
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());

            Thread thread_read = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            String str = dis.readUTF();
                            if (str.equals("/end")) {
                                System.out.println("Пришла команда завершения соединения. Разраываем соединение на клиенте.");
                                closeConnection(socket, dis, dos);
                                break;
                            }
                            System.out.println("Сервер: " + str);

                        }
                    } catch (IOException ioException) {
                        System.out.println("Произошло исключение на клиенте при чтении из потока.");
                        closeConnection(socket, dis, dos);
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
//                            System.out.print(">");
                            Scanner scanner = new Scanner(System.in);
                            String str = scanner.nextLine();
                            dos.writeUTF(str);
                            if (str.equals("/end")) {
                                System.out.println("Пришла команда завершения соединения. Разраываем соединение на клиенте.");
                                closeConnection(socket, dis, dos);
                                break;
                            }
                        }
                    } catch (IOException ioException) {
                        System.out.println("Произошло исключение на клиенте при записи в поток.");
                        closeConnection(socket, dis, dos);
                        ioException.printStackTrace();
                    }
                }
            });
            thread_write.start();

        } catch (Exception ex) {
            System.out.println("Произошло исключение на клиенте");
            ex.printStackTrace();
        }
    }

    private void closeConnection(Socket socket, DataInputStream dis, DataOutputStream dos) {
        try {
            dis.close();
            System.out.println("dis close");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            dos.close();
            System.out.println("dos close");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            socket.close();
            System.out.println("socket close");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void btnClickSend(ActionEvent actionEvent) {
        mainChat.setText(mainChat.getText() + messageField.getText() + "\n");
        messageField.clear();

        try {
           // while (true) {
                dos.writeUTF(mainChat.getText());
          //  }
        } catch (IOException ioException) {
            System.out.println("Произошло исключение на клиенте при записи в поток.");
            ioException.printStackTrace();
        }
    }

    public void btnClickAuth(ActionEvent actionEvent) {
        createConnection();
        try {
            dos.writeUTF("/auth " + loginField.getText() + " " + passField.getText());
            loginField.clear();
            passField.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void sendMessage() {
//        if (!msgInputField.getText().trim().isEmpty()) {
//            try {
//                out.writeUTF(msgInputField.getText());
//                msgInputField.setText("");
//                msgInputField.grabFocus();
//            } catch (IOException e) {
//                e.printStackTrace();
//                JOptionPane.showMessageDialog(null, "Ошибка отправки сообщения");
//            }
//        }
    }
}
