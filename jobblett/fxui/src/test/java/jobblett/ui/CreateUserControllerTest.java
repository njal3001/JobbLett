package jobblett.ui;

import static jobblett.ui.JobblettScenes.CREATE_USER;
import static jobblett.ui.JobblettScenes.LOGIN;
import static jobblett.ui.JobblettScenes.USER_HOME;

import jobblett.core.Group;
import jobblett.core.User;
import org.junit.jupiter.api.Test;

public class CreateUserControllerTest extends JobbLettTest {

  @Override protected JobblettScenes giveId() {
    return CREATE_USER;
  }


  @Test public void testErrorMessage_EmptyAfterInitalization() {
    uiAssertions.assertLabel("errorMessage", "");
  }

  @Test public void testGoToLogin() {
    clickOn("#goBackButton");
    uiAssertions.assertOnScene(LOGIN);
  }

  @Test public void testCreateUser_invalidUserData() {
    tryToCreateUser("", "", "", "");
    uiAssertions.assertOnScene(CREATE_USER);
    uiAssertions.assertLabel("errorMessage",
        "Not a valid password\nNot a valid username\nNot a valid name");
  }

  @Test public void testCreateUser_validUserData() {
    tryToCreateUser("Test3", "Password12345", "Ole", "Dole");
    uiAssertions.assertOnScene(USER_HOME);
    uiAssertions.assertLabel("userFullName", "Ole Dole");
  }

  @Test public void testCreateUser_usernameTaken() {
    tryToCreateUser("CorrectUsername", "CorrectPassword12345", "Ole", "Dole");
    uiAssertions.assertOnScene(CREATE_USER);
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
