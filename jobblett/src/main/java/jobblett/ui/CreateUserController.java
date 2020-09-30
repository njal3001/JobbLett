package jobblett.ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import jobblett.core.AbstractUser;



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

    boolean admin;
    
    @FXML
    Button goBack;


    @FXML
    public void CreateAccount() throws IOException {
        String username = this.username.getText();
        String password = this.password.getText();
        String givenName = this.givenName.getText();
        String familyName = this.familyName.getText();
        try {
        	AbstractUser newUser= main.getUserList().newUser(username,password,givenName,familyName, admin);
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
