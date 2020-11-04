package jobblett.ui;

import static jobblett.ui.JobblettScenes.CREATE_USER;
import static jobblett.ui.JobblettScenes.LOGIN;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

public class JobblettScenesTest extends ApplicationTest {

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

  @Test public void testLoadedScenes() {
    assertNotNull(LOGIN.getScene());
    assertNotNull(LOGIN.getController());
    assertNotNull(CREATE_USER.getScene());
    assertNotNull(CREATE_USER.getController());
  }

}
