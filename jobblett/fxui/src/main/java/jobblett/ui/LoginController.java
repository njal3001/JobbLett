package jobblett.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
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
  Text username;

  @FXML
  Text password;

  @FXML
  PasswordField passwordField;

  @Override
  public void styleIt() {
    super.styleIt();
    login.setSkin(new JobblettButtonSkin(login));
    createAccount.setSkin(new JobblettButtonSkin(createAccount));
    errorMessage.setFont(font);
    username.setFont(font);
    password.setFont(font);
  }

  @Override
  public void onSceneDisplayed() {
    super.onSceneDisplayed();
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
    User user = getAccess().login(userName,password);
    if (user == null)
      errorMessage.setText("Wrong username or password");
    else {
      mainController.setActiveUser(user);
      mainController.setScene(App.USER_HOME_ID);
    }
  }
}
