package jobblett.ui.remote;

import javafx.application.Application;
import javafx.stage.Stage;
import jobblett.core.Workspace;
import jobblett.restserver.JobblettConfig;
import jobblett.ui.ControllerMap;
import jobblett.ui.JobbLettTest;
import jobblett.ui.RemoteWorkspaceAccess;
import jobblett.ui.SceneController;

import java.net.URI;

public class TestStart extends Application {
  private JobbLettTest jobbLettTest;
  private SceneController controller;
  private TestServerStarter testServerStarter;

  public TestStart(JobbLettTest jobbLettTest) {
    this.jobbLettTest = jobbLettTest;
  }

  @Override public void start(Stage stage) throws Exception {
    RemoteWorkspaceAccess remoteWorkspaceAccess = new RemoteWorkspaceAccess(new URI("http://localhost:8999/jobblett/"));
    jobbLettTest.controllerMap = new ControllerMap(stage, remoteWorkspaceAccess);
    jobbLettTest.workspace = new Workspace();
    testServerStarter = new TestServerStarter(jobbLettTest.workspace);
    Thread thread = new Thread(testServerStarter);
    thread.start();
    jobbLettTest.setupData();
    if(jobbLettTest.optionalActiveUser() != null) {
      jobbLettTest.controllerMap.setActiveUsername(jobbLettTest.optionalActiveUser().getUsername());
    }
    if(jobbLettTest.optionalActiveGroup() != null) {
      jobbLettTest.controllerMap.setActiveGroupId(jobbLettTest.optionalActiveGroup().getGroupId());
    }
    jobbLettTest.controllerMap.switchScene(jobbLettTest.giveId());
    stage.show();
    controller = jobbLettTest.controllerMap.getController(jobbLettTest.giveId());
  }

  public TestServerStarter getTestServerStarter() {
    return testServerStarter;
  }

  public SceneController getController() {
    return controller;
  }
}
