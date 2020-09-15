package bolett.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;



public class CreateUserController extends AbstractController {

    @FXML
    Button createAccount2;

    @FXML
    public void GoToyourHomePage() throws IOException {
        changeScreen("Welcome.fxml", createAccount2);
    }
}
