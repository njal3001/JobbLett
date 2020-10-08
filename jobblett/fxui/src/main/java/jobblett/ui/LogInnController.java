package jobblett.ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import jobblett.core.Main;
import jobblett.core.User;
import jobblett.json.JSONDeserialize;

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

    // Må fikse teksten på FXML

    public LogInnController() {
        super.main = new Main();
        new JSONDeserialize().updateMain(super.main);
    }

    @Override
    public void update(){
        errorMessage.setText("");
    }
    @FXML
    public void goToCreateUser() throws IOException {
        changeScreen(new FXMLLoader(getClass().getResource("CreateUser.fxml")), createAccount, main);
    }

   @FXML
    public void logInToUserHome() throws IOException{
        String userName = this.userName.getText();
        String password = this.passwordField.getText();
        User user = main.getUserList().login(userName, password);
        if (user == null) 
            errorMessage.setText("Wrong username or password");
        else {
        	main.logIn(user);
            changeScreen(new FXMLLoader(getClass().getResource("UserHome.fxml")), login, main);
        	}
        }


}
