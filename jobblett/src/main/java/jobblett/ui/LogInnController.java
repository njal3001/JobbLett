package jobblett.ui;

import javafx.scene.control.PasswordField;
import jobblett.core.User;
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
    Text infoArea;

    @FXML
    Text errorMessage;

    @FXML
    PasswordField passwordField;

    @FXML
    public void initialize(){
        errorMessage.setVisible(false);
    }
    @FXML
    public void goToCreateUser() throws IOException {
        changeScreen("CreateUser.fxml", createAccount);
    }

    @FXML
    public void logInToUserHome(){
        String userName = this.userName.getText();
        String password = this.passwordField.getText();
        User user = main.getUserList().login(userName, password);
        if (user == null) infoArea.setText("Wrong username or password");
        else {

            try {
                main.logIn(user);
                errorMessage.setVisible(false);
                changeScreen("UserHome.fxml", login);
            } catch (IOException e) {
                e.printStackTrace();
                errorMessage.setVisible(true);
                errorMessage.setText("Wrong username or password");
            }
        }
    }


}
