package lesson8;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientChat extends Application {

    private static final String SERVER_ADDR = "localhost";
    private static final int SERVER_PORT = 8189;
    private Socket socket;
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
    public ListView<String> userList;
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

    @Override
    public void stop() {
        if (socket != null) {
            if (socket.isConnected()) {
                closeConnection();
            }
        }
     }

    public void createConnection() {


        try {
            socket = new Socket(SERVER_ADDR, SERVER_PORT);
            System.out.println("Соединение с сервером установлено");
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());

            Thread thread_read = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            String str = dis.readUTF();
                            if (str.equals(ChatConstants.STOP_WORD) || str.equals(ChatConstants.LOGOUT_COMMAND)) {
                                System.out.println("Пришла команда завершения соединения. Разраываем соединение на клиенте.");
                                break;
                            } else if (str.startsWith(ChatConstants.CLIENTS_LIST)) {
                                String[] parts = str.split("\\s");
                                if (parts.length > 1) {
                                    userList.setItems(FXCollections.observableArrayList());
                                    ObservableList<String> items = FXCollections.observableArrayList ();
                                    for (int i = 1; i < parts.length; i++) {
                                        items.add(parts[i]);
                                    }
                                    userList.setItems(items);
                                } else {

                                    userList.setItems(FXCollections.observableArrayList());
                                }
                                continue;
                            } else if (str.startsWith(ChatConstants.AUTH_OK)) {

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
                        ioException.printStackTrace();
                    }
                }
            });
            thread_read.start();

        } catch (Exception ex) {
            System.out.println("Произошло исключение на клиенте");
            ex.printStackTrace();
        }
    }

    public void btnClickSend(ActionEvent actionEvent) {
        try {
            dos.writeUTF(messageField.getText());
        } catch (IOException ioException) {
            System.out.println("Произошло исключение на клиенте при записи в поток.");
            ioException.printStackTrace();
        }
        messageField.clear();
    }

    public void btnClickAuth(ActionEvent actionEvent) {

        if(socket == null) {
            createConnection();
        }

        if (btnAuth.getText().equals(ChatConstants.EXIT_TEXT)) {

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    loginField.setVisible(true);
                    passField.setVisible(true);
                    mainChat.clear();
                    userList.setItems(FXCollections.observableArrayList());
                    labelNick.setText("");
                    btnAuth.setText(ChatConstants.AUTH_TEXT);
                }
            });

            try {
                dos.writeUTF(ChatConstants.LOGOUT_COMMAND);
                closeConnection();
            } catch (IOException ioException) {
                System.out.println("Произошло исключение на клиенте при записи в поток.");
                ioException.printStackTrace();
            }

        } else {


            try {
                dos.writeUTF(ChatConstants.AUTH_COMMAND + " " + loginField.getText() + " " + passField.getText());
                loginField.clear();
                passField.clear();
            } catch (Exception e) {
                e.printStackTrace();
            }

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

    public void userListClicked(MouseEvent event) {

        String messageText = messageField.getText().replaceAll(ChatConstants.SEND_TO_LIST + " ", "");
        messageText = messageText.replaceAll("/" + userList.getSelectionModel().getSelectedItem() + " ", "");
        messageField.setText(ChatConstants.SEND_TO_LIST + " /" + userList.getSelectionModel().getSelectedItem() + " " + messageText);
    }

    public void closeConnection() {
        try {
            dis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            dos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
