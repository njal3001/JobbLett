package jobblett.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
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
    // assertTrue(controller instanceof CreateUserController);
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
    //sjekker at den finner nodene osm finnes i loginfxml, som bekrefter at den byttet scene
    TextField nodeInLoginScene = lookup("#usernameField").query();
    assertNotNull(nodeInLoginScene);
    System.out.println("dfsjhudjsj");
  }
@Test
public void dummy_test() {
  assertTrue(true);
 }
}
