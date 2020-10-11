package jobblett.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;
import jobblett.core.Main;

public class CreateUserControllerTest extends ApplicationTest {
  private AbstractController controller;
  private Main main;

  public void start(final Stage primaryStage) throws Exception {
    // muligens en testFXMLfil
    final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CreateUser.fxml"));
    final Parent parent = fxmlLoader.load();
    this.controller = fxmlLoader.getController();
    // fiks en getMain i abstraktkontroller
    this.main = this.controller.main;
    primaryStage.setScene(new Scene(parent));
    primaryStage.show();
  }
  

  @Test
  public void testController() {
    assertNotNull(controller);
  }
//fjerne??
  @Test
  public void testErrorMessages() {
    Text errorMessage = lookup("#errorMessage").query();
    assertEquals("", errorMessage.getText());
    clickOn("#createAccountButton");
    assertNotEquals("",errorMessage.getText());
  }

  @Test
  public void testGoToLogin(){
    clickOn("#goBackButton");
    //sjekker at den finner nodene osm finnes i loginfxml, som bekrefter at den byttet scene
    TextField nodeInLoginScene = lookup("#usernameField").query();
    assertNotNull(nodeInLoginScene);
  }
  
@Test
public void testCreateInvalidAccount() {
  //Creating an invalid account, should not change screen
  clickOn("#username").write("invalid username");
  clickOn("#password").write("invalidpassword");
  clickOn("#givenName").write("wr0ngN4me");
  clickOn("#familyName").write("n4me");
  clickOn("#createAccountButton");
  //confirm errormessage, fjerne?
  assertNotEquals("", ((Text)lookup("#errorMessage").query()).getText());
  //confirm that scene has not changed, by confirming that we can find a node in createuseFXML
  TextField nodeInCreateAccountScene = lookup("#username").query();
  assertNotNull(nodeInCreateAccountScene);
 }

public void testCreateValidAccount(){
   //Creating an valid account, should change screen
  clickOn("#username").
  clickOn("#username").write("Validuser1");
  clickOn("#password").write("ValidPass123");
  clickOn("#givenName").write("valid");
  clickOn("#familyName").write("name");
  clickOn("#createAccountButton");
  //confirm no errormessage
  assertEquals("", ((Text) lookup("#errorMessage").query()).getText());
  //confirm that scene changed, by confirming that we can find a node in UserHomeFXML
  TextField nodeInUserHomeScene = lookup("#createGroupButton").query();
  assertNotNull(nodeInUserHomeScene);
}
}
