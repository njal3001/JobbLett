package jobblett.ui;

import static jobblett.ui.JobblettScenes.CREATE_USER;
import static jobblett.ui.JobblettScenes.LOGIN;
import static jobblett.ui.SceneController.switchScene;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

public class SceneControllerTest extends ApplicationTest {

  // Vet ikke helt hvordan vi skal lage denne testen

  @Override public void start(final Stage primaryStage) throws IOException {
    for (JobblettScenes jobblettScenes : JobblettScenes.values()) {
      jobblettScenes.reset();
    }
    SceneController.setStage(primaryStage);
    if (App.REST_API_ON) {
      try {
        // Has to be updated to the right URI
        SceneController.setAccess(new JobblettRemoteAccess(new URI("")));
      } catch (URISyntaxException e) {
        e.printStackTrace();
      }
    } else {
      SceneController.setAccess(new JobblettDirectAccess());
    }
  }

  @Test public void testSetScene() {
    Platform.runLater(() -> {
      switchScene(LOGIN);
      assertEquals(LOGIN.getScene(), SceneController.getStage().getScene());
      switchScene(CREATE_USER);
      assertEquals(CREATE_USER.getScene(), SceneController.getStage().getScene());
    });
  }
}
