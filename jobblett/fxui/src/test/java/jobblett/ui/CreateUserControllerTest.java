package jobblett.ui;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import javafx.scene.control.TextField;
import jobblett.core.Group;
import jobblett.core.User;

public class CreateUserControllerTest extends JobbLettTest {

  @Override
  protected String giveID() {
    return App.CREATE_USER_ID;
  }

  @Override
  protected User giveActiveUser(){
    return null;
  }

  @Override
  protected Group giveActiveGroup(){
    return null;
  }

  @Test
  public void testErrorMessage_EmptyAfterInitalization() {
    uiAssertions.assertText("errorMessage", "");
  }

  @Test
  public void testGoToLogin() {
    clickOn("#goBackButton");
    uiAssertions.assertOnScene(App.LOGIN_ID);
  }

  @Test
  public void testCreateUser_invalidUserData() {
    tryToCreateUser("", "", "", "");
    uiAssertions.assertOnScene(App.CREATE_USER_ID);
    uiAssertions.assertText("errorMessage", "Not a valid password\nNot a valid username\nNot a valid name");
  }

  @Test
  public void testCreateUser_validUserData() {
    tryToCreateUser("Test3", "Password12345", "Ole", "Dole");
    uiAssertions.assertOnScene(App.USER_HOME_ID);
    uiAssertions.assertText("userFullName", "Ole Dole");
  }

  @Test
  public void testCreateUser_usernameTaken() {
    tryToCreateUser("CorrectUsername", "CorrectPassword12345", "Ole", "Dole");
    uiAssertions.assertOnScene(App.CREATE_USER_ID);
    uiAssertions.assertText("errorMessage", "User with the same username already exists");
  }

  private void tryToCreateUser(String username, String password, String givenName, String familyName) {
    clickOn("#username").write(username);
    clickOn("#password").write(password);
    clickOn("#givenName").write(givenName);
    clickOn("#familyName").write(familyName);
    clickOn("#createAccountButton");
  }
}
