package lesson8;

import java.util.Optional;

public class AuthTest {
    public static void main(String[] args) {
        AuthService authService = new BaseAuthService();
        authService.start();

        String nick = authService.getNickFromLoginAndPass("login1", "pass1");
        System.out.println(nick);
    }
}
