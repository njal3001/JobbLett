package jobblett.ui;

import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;

public class RemoteShiftViewControllerTest extends ShiftViewControllerTest {

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