package jobblett.ui;

import static jobblett.ui.JobblettScenes.CREATE_USER;
import static jobblett.ui.JobblettScenes.LOGIN;
import static jobblett.ui.JobblettScenes.USER_HOME;

import jobblett.core.Group;
import jobblett.core.User;
import org.junit.jupiter.api.Test;

public class LoginControllerTest extends JobbLettTest {

  @Override protected JobblettScenes giveId() {
    return LOGIN;
  }

  @Test public void testLogin_wrongPasswordAndUsername() {
    tryToLogin("WrongUsername", "WrongPassword12345");
    uiAssertions.assertOnScene(LOGIN);
    uiAssertions.assertLabel("errorMessage", "Wrong username or password");
  }

  @Test public void testLogin_correctPasswordAndUsername() {
    tryToLogin(user1.getUserName(), "CorrectPassword12345");
    uiAssertions.assertOnScene(USER_HOME);
    uiAssertions.assertLabel("userFullName", user1.getGivenName() + " " + user1.getFamilyName());
  }

  @Test public void testGoToCreateAccount() {
    clickOn("#createAccount");
    uiAssertions.assertOnScene(CREATE_USER);
  }

  private void tryToLogin(String username, String password) {
    clickOn("#usernameField").write(username);
    clickOn("#passwordField").write(password);
    clickOn("#login");
  }
}
