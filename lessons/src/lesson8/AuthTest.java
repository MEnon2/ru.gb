package lesson8;

import java.util.Optional;

public class AuthTest {
    public static void main(String[] args) {
        AuthService authService = new BaseAuthService();
        authService.start();

        Optional<String> oNick = authService.getNickFromLoginAndPass("login1", "pass1");
        if(oNick.isPresent()) {
            System.out.println(oNick.get());
        }
    }
}
