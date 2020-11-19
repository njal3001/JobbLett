package jobblett.ui.remote;

import javafx.stage.Stage;
import jobblett.ui.JoinGroupControllerTest;
import jobblett.ui.UpdateShiftControllerTest;
import org.junit.jupiter.api.AfterEach;

public class RemoteUpdateShiftControllerTest extends UpdateShiftControllerTest {
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
