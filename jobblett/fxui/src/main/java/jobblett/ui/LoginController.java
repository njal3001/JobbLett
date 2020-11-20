package jobblett.ui;

import static jobblett.ui.JobblettScenes.CREATE_USER;
import static jobblett.ui.JobblettScenes.USER_HOME;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class LoginController extends SceneController {

  @FXML
  Button createAccount;

  @FXML
  Button login;

  @FXML
  TextField usernameField;

  @FXML
  Label errorMessage;

  @FXML
  Label username;

  @FXML
  Label password;

  @FXML
  PasswordField passwordField;

  @Override
  public void styleIt() {
    super.styleIt();
    login.setSkin(new ButtonAnimationSkin(login));
    createAccount.setSkin(new ButtonAnimationSkin(createAccount));
  }

  @FXML
  public void goToCreateUser() {
    switchScene(CREATE_USER);
  }

  /**
   * Logging the user in.
   */
  @FXML
  public void logInToUserHome() {
    String username = this.usernameField.getText();
    String password = this.passwordField.getText();
    if (getAccess().correctPassword(username, password)) {
      getControllerMap().setActiveUsername(username);
      switchScene(USER_HOME);
    } else {
      errorMessage.setText("Wrong username or password");
    }
  }
}
