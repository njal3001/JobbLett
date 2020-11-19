package jobblett.ui;

import static jobblett.ui.JobblettScenes.CREATE_USER;
import static jobblett.ui.JobblettScenes.LOGIN;
import static jobblett.ui.JobblettScenes.USER_HOME;

import org.junit.jupiter.api.Test;

public class CreateUserControllerTest extends JobbLettTest {

  @Override public JobblettScenes giveId() {
    return CREATE_USER;
  }


  @Test public void testErrorMessage_usernameCriteriaOnInitalization() {
    uiAssertions.assertLabel("errorMessage", CreateUserController.usernameCriteria);
  }

  @Test public void testGoToLogin() {
    clickOn("#goBackButton");
    uiAssertions.assertOnScene(LOGIN);
  }

  //TODO: Nå testes ikke hver feilmelding
  @Test public void testCreateUser_invalidUserData() {
    tryToCreateUser("", "", "", "");
    uiAssertions.assertOnScene(CREATE_USER);
    uiAssertions.assertLabel("errorMessage",
        "Not a valid password");
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
    clickOn("#usernameField").write(username);
    clickOn("#passwordField").write(password);
    clickOn("#givenNameField").write(givenName);
    clickOn("#familyNameField").write(familyName);
    clickOn("#createAccountButton");
  }
}
