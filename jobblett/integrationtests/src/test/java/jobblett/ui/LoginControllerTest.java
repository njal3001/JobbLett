package jobblett.ui;

import org.junit.jupiter.api.Test;

import static jobblett.ui.JobblettScenes.*;

public abstract class LoginControllerTest extends JobbLettTest {

  @Override public JobblettScenes giveId() {
    return LOGIN;
  }

  @Test public void testLogin_wrongPasswordAndUsername() {
    tryToLogin("WrongUsername", "WrongPassword12345");
    uiAssertions.assertOnScene(LOGIN);
    uiAssertions.assertLabel("errorMessage", "Wrong username or password");
  }

  @Test public void testLogin_correctPasswordAndUsername() {
    tryToLogin(user1.getUsername(), "CorrectPassword12345");
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
