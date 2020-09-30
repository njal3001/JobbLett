package jobblett.ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import jobblett.core.AbstractUser;

public class LogInnController extends AbstractController {

    @FXML
    Button createAccount;

    @FXML
    Button login;

    @FXML
    TextField userName;

    @FXML
    Text errorMessage;

    @FXML
    PasswordField passwordField;

    @FXML
    public void initialize(){
        //errorMessage.setVisible(false);
    }
    @FXML
    public void goToCreateUser() throws IOException {
        changeScreen("CreateUser.fxml", createAccount);
    }

   @FXML
    public void logInToUserHome() throws IOException{
        String userName = this.userName.getText();
        String password = this.passwordField.getText();
        AbstractUser user = main.getUserList().login(userName, password);
        if (user == null) errorMessage.setText("Wrong username or password");
        else {
        	main.logIn(user);
            changeScreen("UserHome.fxml", login);
        	}
        }


}
