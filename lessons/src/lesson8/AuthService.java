package lesson8;

import java.util.Optional;

public interface AuthService {

    void start();

    void stop();

    String getNickFromLoginAndPass(String login, String pass);



}
