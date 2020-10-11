package jobblett.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class CreateUserControllerTest extends JobbLettTest {

  @Override
  public void start(final Stage stage) throws Exception {
    fxmlFileName = "CreateUser.fxml";
    super.start(stage);
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
}
