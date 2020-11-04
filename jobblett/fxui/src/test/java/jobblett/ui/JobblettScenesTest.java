package jobblett.ui;

import static jobblett.ui.JobblettScenes.CREATE_USER_ID;
import static jobblett.ui.JobblettScenes.LOGIN_ID;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

public class JobblettScenesTest extends ApplicationTest {

  @Override public void start(final Stage primaryStage) throws IOException {
    App.loadScenes(primaryStage);
  }

  @Test public void testLoadedScenes() {
    assertNotNull(LOGIN_ID.getScene());
    assertNotNull(LOGIN_ID.getController());
    assertNotNull(CREATE_USER_ID.getScene());
    assertNotNull(CREATE_USER_ID.getController());
  }

}
