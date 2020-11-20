package jobblett.ui;

import javafx.application.Application;
import javafx.stage.Stage;
import jobblett.core.Workspace;

import java.net.ConnectException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.TimeUnit;

public class TestStart extends Application {
  private JobbLettTest jobbLettTest;
  private SceneController controller;
  private TestServerStarter testServerStarter;

  public TestStart(JobbLettTest jobbLettTest) {
    this.jobbLettTest = jobbLettTest;
  }

  @Override public void start(Stage stage) throws Exception {
    URI uri = new URI("http://localhost:8999/jobblett/");
    RemoteWorkspaceAccess remoteWorkspaceAccess = new RemoteWorkspaceAccess(uri);
    jobbLettTest.controllerMap = new ControllerMap(stage, remoteWorkspaceAccess);
    jobbLettTest.workspace = new Workspace();
    testServerStarter = new TestServerStarter(jobbLettTest.workspace);
    jobbLettTest.setupData();
    Thread thread = new Thread(testServerStarter);
    thread.start();
    for (int i = 0; i < 200; i++) {
      try {
        HttpRequest requestObject = HttpRequest.newBuilder(uri).header("Accept", "application/json").build();
        HttpClient.newBuilder().build().send(requestObject, HttpResponse.BodyHandlers.ofString());
        break;
      } catch (ConnectException e) {
        TimeUnit.MILLISECONDS.sleep(10);
      }
    }
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
