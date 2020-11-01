package jobblett.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import jobblett.core.HashedPassword;
import jobblett.core.User;

public class CreateUserController extends SceneController {

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
  public void createAccount() {
    String username = this.username.getText();
    String passwordString = this.password.getText();
    String givenName = this.givenName.getText();
    String familyName = this.familyName.getText();

    String errorMessageString = "";

    // Bør vel være en bedre måte å gjøre dette på...
    HashedPassword password = HashedPassword.hashPassword("defaultPassword123");
    try {
      password = HashedPassword.hashPassword(passwordString);
    } catch (IllegalArgumentException e) {
      errorMessageString += e.getMessage();
    }
    try{
      User newUser = new User(username, password, givenName, familyName);
      if (errorMessageString.length() == 0) {
        getAccess().add(newUser);
        mainController.setActiveUser(newUser);
        mainController.setScene(App.USER_HOME_ID);
      };
    } catch (IllegalArgumentException e) {
      if (errorMessageString.length() != 0) {
        errorMessageString+="\n";
      }
      errorMessageString += e.getMessage();
    }
    errorMessage.setText(errorMessageString);
  }

  @FXML
  public void goToLogIn(){
    mainController.setScene(App.LOGIN_ID);
  }
}
