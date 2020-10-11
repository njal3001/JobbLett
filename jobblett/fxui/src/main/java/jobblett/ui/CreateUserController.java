package jobblett.ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import jobblett.core.User;

public class CreateUserController extends AbstractController {

    @FXML
    Button createAccountButton;

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
    Button goBackButton;

    @FXML
    public void createAccount() throws IOException {
        String username = this.username.getText();
        String password = this.password.getText();
        String givenName = this.givenName.getText();
        String familyName = this.familyName.getText();
        try {
            User newUser = new User(username, password, givenName, familyName);
            main.getUserList().addUser(newUser);
        	main.logIn(newUser);
        	changeScreen(new FXMLLoader(getClass().getResource("UserHome.fxml")), createAccountButton, main);
        } catch(Exception e) {
            errorMessage.setText(e.getMessage());
        }
    }
    
    @FXML
    public void goToLogIn() throws IOException {
    	changeScreen(new FXMLLoader(getClass().getResource("Login.fxml")), goBackButton, main);
    }
        
}
