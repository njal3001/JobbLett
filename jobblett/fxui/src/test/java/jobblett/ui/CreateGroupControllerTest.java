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
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;
import jobblett.core.Main;

public class CreateGroupControllerTest extends ApplicationTest {
  private AbstractController controller;
  private Main main;

  public void start(final Stage primaryStage) throws Exception {
    // muligens en testFXMLfil
    final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CreateGroup.fxml"));
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
    // assertTrue(controller instanceof CreateUserController);
  }

  @Test
  public void testGoToUserHome(){
    clickOn("#goBackButton");
    //checks if a node in the UserHome.fxml exists on the current screen, which confirms that we are on the correct scene
    Button nodeInUserHomeScene = ((Button) lookup("#createGroupButton").query());
    assertNotNull(nodeInUserHomeScene);
  }

@Test
public void testCreateInvalidGroup() {
  //Creating an invalid group, should not change scene, but give an errormessage
  //clickOn("#groupNameField").write("q");
  System.out.println("1.lookup");
  System.out.println(((Text) lookup("#errorMessage").query()).getText());
  clickOn("#createGroupButton");
  System.out.println("2.lokup");
  System.out.println(((Text) lookup("#errorMessage").query()).getText());
  //confirm errormessage
  assertNotEquals("", ((Text) lookup("#errorMessage").query()).getText());
  //confirm that scene has not been changed, by confirming that we can find a unique node in CreateGroup.fxml
  TextField nodeInCreateGroupScene = lookup("#groupNameField").query();
  assertNotNull(nodeInCreateGroupScene);
 }

public void testCreateValidGroup(){
  //Creating an valid group, should change scene to GroupHome.fxml
  clickOn("#groupNameField").write("Valid Group");
  clickOn("#createGroupButton");
  //confirm  no errormessage
  assertEquals("", ((Text) lookup("#errorMessage").query()).getText());
  //confirm that scene has been changed, by confirming that we can find a unique node in GroupHome.fxml
  TextField nodeInGroupHomeScene = lookup("#groupID").query();
  assertNotNull(nodeInGroupHomeScene);
}
}
