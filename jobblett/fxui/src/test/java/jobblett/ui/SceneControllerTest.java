package jobblett.ui;

import static jobblett.ui.JobblettScenes.CREATE_USER_ID;
import static jobblett.ui.JobblettScenes.LOGIN_ID;
import static jobblett.ui.SceneController.switchScene;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

public class SceneControllerTest extends ApplicationTest {

  // Vet ikke helt hvordan vi skal lage denne testen

  @Override public void start(final Stage primaryStage) throws IOException {
    App.loadScenes(primaryStage);
  }

  @Test public void testSetScene() {
    Platform.runLater(() -> {
      switchScene(LOGIN_ID);
      assertEquals(LOGIN_ID.getScene(), SceneController.getStage().getScene());
      switchScene(CREATE_USER_ID);
      assertEquals(CREATE_USER_ID.getScene(), SceneController.getStage().getScene());
    });
  }
}
