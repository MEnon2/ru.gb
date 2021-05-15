import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientChat {

    private static final String SERVER_ADDR = "localhost";
    private static final int SERVER_PORT = 8189;

    public ClientChat() {
        createConnection();
    }
    private void createConnection() {

        try  {
            Socket socket = new Socket(SERVER_ADDR, SERVER_PORT);
            System.out.println("socket connected");
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

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

}
