package jobblett.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import jobblett.core.HashedPassword;
import jobblett.core.User;

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
    /*errorMessage.setFont(font);
    username.setFont(font);
    password.setFont(font);
    usernameField.setFont(font);*/
  }

  @Override
  public void onSceneDisplayed() {
    usernameField.setText("");
    passwordField.setText("");
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
    User user = getAccess().login(userName, HashedPassword.hashPassword(password));
    if (user == null)
      errorMessage.setText("Wrong username or password");
    else {
      mainController.setActiveUser(user);
      mainController.setScene(App.USER_HOME_ID);
    }
  }
}
