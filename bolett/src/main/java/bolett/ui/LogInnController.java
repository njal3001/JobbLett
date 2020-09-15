package bolett.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class LogInnController extends AbstractController {

    @FXML
    Button createAccount;

    @FXML
    Button login;

    @FXML
    public void goToCreateUser() throws IOException {
        changeScreen("CreateUser.fxml", createAccount);
    }

    @FXML
    public void logInToYourHome() throws IOException {
        changeScreen("YourHome.fxml", login);
    }


}
