package jobblett.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class LoginTest extends JobbLettTest {

  @Override
  protected String giveFxmlFileName() {
    return "Login.fxml";
  }

  @Test
  public void testLogin_wrongPasswordAndUsername(){
    tryToLogin("WrongUsername", "WrongPassword12345");
    Text errorMessage = lookup("#errorMessage").query();
    assertEquals(errorMessage.getText(), "Wrong username or password");
  } 

  @Test
  public void testLogin_correctPasswordAndUsername(){
    tryToLogin(user1.getUserName(), "CorrectPassword12345");
    Text fullNameText = lookup("#userFullName").query();
    assertNotNull(fullNameText);
    assertEquals(user1.getGivenName() + " " + user1.getFamilyName(), fullNameText.getText());
  }

  @Test
  public void testGoToCreateAccount(){
    clickOn("#createAccount");
    Button goBackButton = lookup("#goBackButton").query();
    assertNotNull(goBackButton);
  }

  private void tryToLogin(String username, String password){
    clickOn("#usernameField").write(username);    
    clickOn("#passwordField").write(password);
    clickOn("#login");
  }
}