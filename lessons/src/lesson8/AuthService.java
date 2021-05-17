package lesson8;

public interface AuthService {

    void start();

    void stop();

    String getNickFromLoginAndPass(String login, String pass);



}
