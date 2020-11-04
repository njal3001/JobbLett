package jobblett.ui;

import static jobblett.ui.JobblettScenes.LOGIN_ID;
import static jobblett.ui.JobblettScenes.USER_HOME_ID;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import jobblett.core.HashedPassword;
import jobblett.core.User;

public class CreateUserController extends SceneController {

  @FXML Button createAccountButton;

  @FXML TextField username;

  @FXML PasswordField password;

  @FXML TextField givenName;

  @FXML TextField familyName;

  @FXML Label errorMessage;

  @FXML Button goBackButton;

  @Override public void onSceneDisplayed() {
    username.setText("");
    password.setText("");
    givenName.setText("");
    familyName.setText("");
    errorMessage.setText("");
  }


  @Override
  public void styleIt() {
    super.styleIt();
    goBackButton.setSkin(new ButtonAnimationSkin(goBackButton));
    createAccountButton.setSkin(new ButtonAnimationSkin(createAccountButton));
  }

  /**
   * TODO.
   */
  @FXML public void createAccount() {
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
    try {
      User newUser = new User(username, password, givenName, familyName);
      if (errorMessageString.length() == 0) {
        getAccess().add(newUser);
        setActiveUser(newUser);
        switchScene(USER_HOME_ID);
      }
      ;
    } catch (IllegalArgumentException e) {
      if (errorMessageString.length() != 0) {
        errorMessageString += "\n";
      }
      errorMessageString += e.getMessage();
    }
    errorMessage.setText(errorMessageString);
  }

  @FXML public void goToLogIn() {
    switchScene(LOGIN_ID);
  }
}
