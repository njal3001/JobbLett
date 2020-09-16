package bolett.ui;

import bolett.core.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;

public class LogInnController extends AbstractController {

    @FXML
    Button createAccount;

    @FXML
    Button login;

    @FXML
    TextField userName;

    @FXML
    TextField password;

    @FXML
    Text infoArea;

    @FXML
    public void goToCreateUser() throws IOException {
        changeScreen("CreateUser.fxml", createAccount);
    }

    @FXML
    public void logInToUserHome() throws IOException {
        String userName = this.userName.getText();
        String password = this.password.getText();
        User user = main.getUserList().login(userName, password);
        if (user == null) infoArea.setText("Wrong username or password");
        else {
            main.logIn(user);
            changeScreen("UserHome.fxml", login);
        }
    }


}
