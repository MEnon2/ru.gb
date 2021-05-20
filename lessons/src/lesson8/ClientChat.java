package lesson8;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
    @FXML
    public TextArea userList;
    @FXML
    public Button btnAuth;
    @FXML
    public Label labelNick;

    public static void main(String[] args) {
        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("chat_structure.fxml"));
        primaryStage.setTitle("Сетевой чат на курсе GeekBrains");
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
                                // closeConnection(socket, dis, dos);
                                break;
                            } else if (str.startsWith("/clients")) {
                                String[] parts = str.split("\\s");
                                if (parts.length > 1) {
                                    userList.clear();
                                    for (int i = 1; i < parts.length; i++) {
                                        userList.appendText(parts[i] + "\n");
                                    }
                                } else {
                                    userList.clear();
                                }
                                continue;
                            } else if (str.startsWith("/authok")) {


                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        loginField.setVisible(false);
                                        passField.setVisible(false);
                                        String[] parts = str.split("\\s");
                                        if (parts.length > 1) {
                                            labelNick.setText(parts[1]);
                                        }
                                        btnAuth.setText(ChatConstants.EXIT_TEXT);
                                    }
                                });

                                continue;
                            } else {

                                mainChat.appendText(str + "\n");
                            }


                        }
                    } catch (IOException ioException) {
                        System.out.println("Произошло исключение на клиенте при чтении из потока.");
                        // closeConnection(socket, dis, dos);
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
//                                closeConnection(socket, dis, dos);
                                break;
                            }
                        }
                    } catch (IOException ioException) {
                        System.out.println("Произошло исключение на клиенте при записи в поток.");
//                        closeConnection(socket, dis, dos);
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

    public void btnClickSend(ActionEvent actionEvent) {
        messageField.clear();

        try {
            dos.writeUTF(messageField.getText());
        } catch (IOException ioException) {
            System.out.println("Произошло исключение на клиенте при записи в поток.");
            ioException.printStackTrace();
        }
    }

    public void btnClickAuth(ActionEvent actionEvent) {
        if (btnAuth.getText().equals(ChatConstants.EXIT_TEXT)) {

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    loginField.setVisible(true);
                    passField.setVisible(true);
                    mainChat.clear();
                    userList.clear();
                    labelNick.setText("");
                    btnAuth.setText(ChatConstants.AUTH_TEXT);
                }
            });

            try {
                dos.writeUTF(ChatConstants.LOGOUT_COMMAND);
            } catch (IOException ioException) {
                System.out.println("Произошло исключение на клиенте при записи в поток.");
                ioException.printStackTrace();
            }

        }

        createConnection();
        try {
            dos.writeUTF("/auth " + loginField.getText() + " " + passField.getText());
            loginField.clear();
            passField.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    @FXML
    private void messageFieldPress(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            try {

                dos.writeUTF(messageField.getText());
                messageField.clear();
            } catch (IOException ioException) {
                System.out.println("Произошло исключение на клиенте при записи в поток.");
                ioException.printStackTrace();
            }
        }
    }


}
