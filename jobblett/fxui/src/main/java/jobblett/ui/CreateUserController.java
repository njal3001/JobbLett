package jobblett.ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import jobblett.core.User;

public class CreateUserController extends ScreenController {

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
    String password = this.password.getText();
    String givenName = this.givenName.getText();
    String familyName = this.familyName.getText();

    String errorMessageText = "";

    //Dette burde kanskje heller vært en feilmelding man fikk direkte fra User klassen i core
    if (!User.validUsername(username))
      errorMessageText += "Not a valid username\n";
    if (!User.validPassword(password))
      errorMessageText += "Not a valid password\n";
    if (!User.validName(givenName))
      errorMessageText += "Not a valid given name\n";
    if (!User.validName(familyName))
      errorMessageText += "Not a valid family name";

    User newUser = null;
    try {
      newUser = new User(username, password, givenName, familyName);
    } catch(IllegalArgumentException e){
      errorMessage.setText(errorMessageText);
      return;
    }
    try{
      main.getUserList().addUser(newUser);
      main.logIn(newUser);
      mainController.setScreen(App.USER_HOME_ID);
    } catch (Exception e) {
      errorMessage.setText(e.getMessage());
    }
  }

  @FXML
  public void goToLogIn(){
    mainController.setScreen(App.LOGIN_ID);
  }
}
