package jobblett.ui.remote;

import javafx.stage.Stage;
import jobblett.ui.UpdateShiftControllerTest;
import jobblett.ui.UserHomeControllerTest;
import org.junit.jupiter.api.AfterEach;

public class RemoteUserHomeControllerTest extends UserHomeControllerTest {
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
