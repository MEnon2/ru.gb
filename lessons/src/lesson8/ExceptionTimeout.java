package lesson8;

import java.io.IOException;

public class ExceptionTimeout extends IOException {

    public ExceptionTimeout() {
        System.out.println("Таймаут. Время для авторизации истекло.");
    }
}
