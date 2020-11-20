package jobblett.ui;

import static jobblett.ui.JobblettScenes.LOGIN;
import static jobblett.ui.JobblettScenes.USER_HOME;

import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import jobblett.core.HashedPassword;
import jobblett.core.User;

public class CreateUserController extends SceneController {

  @FXML
  Button createAccountButton;

  @FXML
  TextField usernameField;

  @FXML
  PasswordField passwordField;

  @FXML
  TextField givenNameField;

  @FXML
  TextField familyNameField;

  @FXML
  Label errorMessage;

  @FXML
  Button goBackButton;

  public static final String usernameCriteria = 
      "Username criteria:\nNo whitespace\nAt least 2 characters";

  public static final String passwordCriteria =
      "Password criteria:\nAt least one digit\n At least one lowercase letter\n"
      + "At least one uppercase letter\nNo whitespace.\nAt least 8 characters";

  public static final String nameCriteria = 
      "Name criteria:\nContains only letters\nAt least 2 characters";


  /**
   * Sets listeners on every text field for showing corresponding criteria. 
   */
  @FXML
  public void initialize() {
    List<String> criteria = 
        List.of(usernameCriteria, passwordCriteria, nameCriteria, nameCriteria);
    List<TextField> textFields = 
        List.of(usernameField, passwordField, givenNameField, familyNameField);

    for (int i = 0; i < criteria.size(); i++) {
      final int k = i;
      textFields.get(i).focusedProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue) {
          errorMessage.setText(criteria.get(Integer.valueOf(k)));
        } else {
          errorMessage.setText("");
        }
      });
    }
  }


  @Override
  public void styleIt() {
    super.styleIt();
    goBackButton.setSkin(new ButtonAnimationSkin(goBackButton));
    createAccountButton.setSkin(new ButtonAnimationSkin(createAccountButton));
  }

  /**
   * Creates a new User.
   */
  @FXML
  public void createAccount() {
    String username = this.usernameField.getText();
    String password = this.passwordField.getText();
    String givenName = this.givenNameField.getText();
    String familyName = this.familyNameField.getText();

    try {
      getAccess().addUser(username, password, givenName, familyName);
      setActiveUsername(username);
      switchScene(USER_HOME);
    } catch (Exception e) {
      errorMessage.setText(e.getMessage());
    }
  }

  @FXML
  public void goToLogIn() {
    switchScene(LOGIN);
  }
}
