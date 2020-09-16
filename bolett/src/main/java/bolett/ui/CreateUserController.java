package bolett.ui;

import bolett.core.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;



public class CreateUserController extends AbstractController {

    @FXML
    Button createAccount;

    @FXML
    TextField username;

    @FXML
    TextField password;

    @FXML
    TextField givenName;

    @FXML
    TextField familyName;


    @FXML
    public void CreateAccount() throws IOException {
        String username = this.username.getText();
        String password = this.password.getText();
        String givenName = this.givenName.getText();
        String familyName = this.familyName.getText();
        User newUser = main.getUserList().newUser(username,password,givenName,familyName);
        main.logIn(newUser);
        changeScreen("UserHome.fxml", createAccount);
    }
}
