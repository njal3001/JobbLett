package jobblett.ui.remote;

import javafx.stage.Stage;
import jobblett.core.Workspace;
import jobblett.ui.ControllerMap;
import jobblett.ui.CreateGroupControllerTest;
import jobblett.ui.RemoteWorkspaceAccess;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.net.URI;

public class RemoteCreateGroupControllerTest extends CreateGroupControllerTest {
    TestServerStarter testServerStarter;

  @Override public void start(Stage primaryStage) throws Exception {
    TestStart starter = new TestStart(this);
    starter.start(primaryStage);
    testServerStarter = starter.getTestServerStarter();
    controller = starter.getController();
  }

  @AfterEach
  public void stopServer() {
    testServerStarter.stopServer();
  }
}
