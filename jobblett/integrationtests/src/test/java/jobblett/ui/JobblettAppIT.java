package jobblett.ui;

import javafx.stage.Stage;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.net.URI;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

public class JobblettAppIT extends ApplicationTest {

  private ControllerMap controllerMap;

  @Override
  public void start(final Stage stage) throws Exception {
    String port = System.getProperty("jobblett.port");
    assertNotNull(port, "No jobblett.port system property set");
    URI baseUri = new URI("http://localhost:" + port + "/jobblett/");
    System.out.println("Base RemoteWorkspaceAccess URI: " + baseUri);
    RemoteWorkspaceAccess access = new RemoteWorkspaceAccess(baseUri);
    controllerMap = new ControllerMap(stage, access);
    controllerMap.switchScene(JobblettScenes.LOGIN);
    stage.show();
  }

  @Test
  public void testController_initial() {
    assertNotNull(controllerMap.getStage().getScene());
  }
  
}
