package jobblett.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class CreateUserControllerTest extends JobbLettTest {

  @Override
  protected String giveFxmlFileName() {
    return "CreateUser.fxml";
  }

  @Test
  public void testErrorMessages() {
    Text errorMessage = lookup("#errorMessage").query();
    assertEquals(errorMessage.getText(),"");
    clickOn("#createAccountButton");
    assertNotEquals(errorMessage.getText(),"");
  }

  @Test
  public void testGoToLogin(){
    clickOn("#goBackButton");
    //Checks if it finds a node from Login.fxml, which confirms that the scene has been changed
    TextField nodeInLoginScene = lookup("#usernameField").query();
    assertNotNull(nodeInLoginScene);
  }

  private void assertErrorMessage(String expected){
    Text errorMessage = lookup("#errorMessage").query();
    assertEquals(expected, errorMessage.getText());
  }

  @Test
  public void testCreateUser_invalidUserData(){
    tryToCreateUser("", "", "", "");
    assertErrorMessage("Not a valid username\nNot a valid password\nNot a valid name");
  }
  
  @Test
  public void testCreateUser_validUserData(){
    tryToCreateUser("Test3", "Password12345", "Ole", "Dole");
    Text userFullName = lookup("#userFullName").query();
    assertEquals("Ole Dole", userFullName.getText());
  }

  @Test
  public void testCreateUser_usernameTaken(){
    tryToCreateUser("CorrectUsername", "CorrectPassword12345", "Ole", "Dole");
    assertErrorMessage("User with the same username already exists");
  }

  private void tryToCreateUser(String username, String password, String givenName, String familyName){
    clickOn("#username").write(username);
    clickOn("#password").write(password);
    clickOn("#givenName").write(givenName);
    clickOn("#familyName").write(familyName);
    clickOn("#createAccountButton");
  } 
}
