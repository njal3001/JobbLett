package jobblett.ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import jobblett.core.User;



public class CreateUserController extends AbstractController {

    @FXML
    Button createAccount;

    @FXML
    TextField username;

    @FXML
    PasswordField password;

    @FXML
    TextField givenName;

    @FXML
    TextField familyName;
    
    @FXML
    Text errorMessage;
    
    @FXML
    Button goBack;


    @FXML
    public void CreateAccount() throws IOException {
        String username = this.username.getText();
        String password = this.password.getText();
        String givenName = this.givenName.getText();
        String familyName = this.familyName.getText();
        try {
        	User newUser= main.getUserList().newUser(username,password,givenName,familyName);
        	main.logIn(newUser);
        	changeScreen("UserHome.fxml", createAccount);
        } catch(Exception e) {
        	errorMessage.setText(e.getMessage());
        }
    }
    
    @FXML
    public void goToLogIn() throws IOException {
    	changeScreen("Login.fxml", goBack);
    }
        
}
