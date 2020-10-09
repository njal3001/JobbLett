package jobblett.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;
import jobblett.core.Main;
import jobblett.core.User;

public class LoginTest extends ApplicationTest {

  // Må endre på testdata i forhold til json

  private LoginController controller;

  //Midlertidig?
  private User user;

  @Override
  public void start(final Stage stage) throws Exception{
    FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
    Parent root = loader.load();
    controller = loader.getController();
    stage.setScene(new Scene(root));
    stage.show();

    //Midlertidig
    controller.setMain(getMain());
  }

  //Midlertidig
  private Main getMain(){
     user = new User("CorrectUsername", "CorrectPassword12345", "Ole", "Dole");
     Main main = new Main();
     main.getUserList().addUser(user);
     return main;
  }

  @Test
  public void testController(){
    assertNotNull(controller);
  }

  @Test
  public void testLogin_wrongPasswordAndUsername(){
    tryToLogin("WrongUsername", "WrongPassword12345");
    Text errorMessage = lookup("#errorMessage").query();
    assertEquals(errorMessage.getText(), "Wrong username or password");
  } 

  @Test
  public void testLogin_correctPasswordAndUsername(){
    tryToLogin(user.getUserName(), "CorrectPassword12345");
    Text fullNameText = lookup("#userFullName").query();
    assertNotNull(fullNameText);
    assertEquals(user.getGivenName() + " " + user.getFamilyName(), fullNameText.getText());
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