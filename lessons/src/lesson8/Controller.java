package lesson8;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Controller {

    @FXML
    public TextField messageField;
    @FXML
    public TextArea mainChat;

    public void btnClick(ActionEvent actionEvent) {

        mainChat.setText(mainChat.getText() + messageField.getText() + "\n");
        messageField.clear();
    }
}
