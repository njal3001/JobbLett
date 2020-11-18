package jobblett.ui.remote;

import javafx.stage.Stage;
import jobblett.ui.CreateUserControllerTest;
import jobblett.ui.GroupHomeControllerTest;
import jobblett.ui.JoinGroupControllerTest;
import org.junit.jupiter.api.AfterEach;

public class RemoteJoinGroupControllerTest extends JoinGroupControllerTest {
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
