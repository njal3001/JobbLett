package jobblett.ui;

import static jobblett.ui.JobblettScenes.CREATE_USER_ID;
import static jobblett.ui.JobblettScenes.LOGIN_ID;
import static jobblett.ui.JobblettScenes.USER_HOME_ID;

import jobblett.core.Group;
import jobblett.core.User;
import org.junit.jupiter.api.Test;

public class CreateUserControllerTest extends JobbLettTest {

  @Override protected JobblettScenes giveID() {
    return CREATE_USER_ID;
  }

  @Override protected User giveActiveUser() {
    return null;
  }

  @Override protected Group giveActiveGroup() {
    return null;
  }

  @Test public void testErrorMessage_EmptyAfterInitalization() {
    uiAssertions.assertLabel("errorMessage", "");
  }

  @Test public void testGoToLogin() {
    clickOn("#goBackButton");
    uiAssertions.assertOnScene(LOGIN_ID);
  }

  @Test public void testCreateUser_invalidUserData() {
    tryToCreateUser("", "", "", "");
    uiAssertions.assertOnScene(CREATE_USER_ID);
    uiAssertions.assertLabel("errorMessage",
        "Not a valid password\nNot a valid username\nNot a valid name");
  }

  @Test public void testCreateUser_validUserData() {
    tryToCreateUser("Test3", "Password12345", "Ole", "Dole");
    uiAssertions.assertOnScene(USER_HOME_ID);
    uiAssertions.assertLabel("userFullName", "Ole Dole");
  }

  @Test public void testCreateUser_usernameTaken() {
    tryToCreateUser("CorrectUsername", "CorrectPassword12345", "Ole", "Dole");
    uiAssertions.assertOnScene(CREATE_USER_ID);
    uiAssertions.assertLabel("errorMessage", "User with the same username already exists");
  }

  private void tryToCreateUser(String username, String password, String givenName,
      String familyName) {
    clickOn("#username").write(username);
    clickOn("#password").write(password);
    clickOn("#givenName").write(givenName);
    clickOn("#familyName").write(familyName);
    clickOn("#createAccountButton");
  }
}
