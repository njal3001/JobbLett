package jobblett.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.application.Platform;
import javafx.stage.Stage;

public class MainControllerTest extends ApplicationTest {

  private MainController mainController;

  // Vet ikke helt hvordan vi skal lage denne testen

  @Override
  public void start(final Stage primaryStage) throws IOException {
    mainController = new MainController(primaryStage);
    mainController.loadScene(App.LOGIN_ID, App.LOGIN_FILE);
    mainController.loadScene(App.CREATE_USER_ID, App.CREATE_USER_FILE);
  }

  @Test
  public void testLoadedScenes() {
    assertNotNull(mainController.getScene(App.LOGIN_ID));
    assertNotNull(mainController.getSceneController(App.LOGIN_ID));
    assertNotNull(mainController.getScene(App.CREATE_USER_ID));
    assertNotNull(mainController.getSceneController(App.CREATE_USER_ID));
  }

  @Test
  public void testSetScene() {
    Platform.runLater(() -> {
      mainController.setScene(App.LOGIN_ID);
      assertEquals(mainController.getScene(App.LOGIN_ID), mainController.getStage().getScene());
      mainController.setScene(App.CREATE_USER_ID);
      assertEquals(mainController.getScene(App.CREATE_USER_ID), mainController.getStage().getScene());

    });
  }
}