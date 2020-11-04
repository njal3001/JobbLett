package jobblett.ui;

import static jobblett.ui.JobblettScenes.CREATE_USER;
import static jobblett.ui.JobblettScenes.USER_HOME;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import jobblett.core.HashedPassword;
import jobblett.core.User;

public class LoginController extends SceneController {

  @FXML Button createAccount;

  @FXML Button login;

  @FXML TextField usernameField;

  @FXML Label errorMessage;

  @FXML Label username;

  @FXML Label password;

  @FXML PasswordField passwordField;

  @Override public void styleIt() {
    super.styleIt();
    login.setSkin(new ButtonAnimationSkin(login));
    createAccount.setSkin(new ButtonAnimationSkin(createAccount));
    /*errorMessage.setFont(font);
    username.setFont(font);
    password.setFont(font);
    usernameField.setFont(font);*/
  }

  @Override public void onSceneDisplayed() {
    usernameField.setText("");
    passwordField.setText("");
    errorMessage.setText("");
  }

  @FXML public void goToCreateUser() {
    switchScene(CREATE_USER);
  }

  /**
   * TODO.
   */
  @FXML public void logInToUserHome() {
    String userName = this.usernameField.getText();
    String password = this.passwordField.getText();
    User user = getAccess().login(userName, HashedPassword.hashPassword(password));
    if (user == null) {
      errorMessage.setText("Wrong username or password");
    } else {
      setActiveUser(user);
      switchScene(USER_HOME);
    }
  }
}
