package jobblett.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import jobblett.core.User;

public class LoginController extends SceneController {

  @FXML
  Button createAccount;

  @FXML
  Button login;

  @FXML
  TextField usernameField;

  @FXML
  Text errorMessage;

  @FXML
  PasswordField passwordField;

  @Override
  public void onSceneDisplayed() {
    errorMessage.setText("");
  }

  @FXML
  public void goToCreateUser(){
    mainController.setScene(App.CREATE_USER_ID);
  }

  @FXML
  public void logInToUserHome(){
    String userName = this.usernameField.getText();
    String password = this.passwordField.getText();
    User user = mainController.getUserList().login(userName, password);
    if (user == null)
      errorMessage.setText("Wrong username or password");
    else {
      mainController.setActiveUser(user);
      mainController.setScene(App.USER_HOME_ID);
    }
  }
}
