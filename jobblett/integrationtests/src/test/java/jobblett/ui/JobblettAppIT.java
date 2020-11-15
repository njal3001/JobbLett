package jobblett.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import jobblett.json.JobblettPersistence;
import jobblett.ui.RemoteApp;


public class JobblettAppIT extends ApplicationTest{

  //private JobblettRemoteAccess controller;

  /*@Override
  public void start(final Stage stage) throws Exception{

    final FXMLLoader loader = new FXMLLoader(getClass().getResource("JobblettApp_it.fxml"));
    final Parent root = loader.load();
    this.controller = loader.getController();
    stage.setScene(new Scene(root));
    stage.show();
  }*/

@Override
public void start(final Stage stage) throws Exception{
  new RemoteApp().start(stage);

}
@BeforeEach
public void setUpGroupList() throws URISyntaxException{
  try(Reader reader = new InputStreamReader(JobblettPersistence.class.getResourceAsStream("defaultGrouplist.json"))) {
    String port = System.getProperty("jobblett.port");
    assertNotNull(port,"No jobblett.port system property set");
    URI baseUri = new URI("http://localhost:" + port + "/jobblett/");
    System.out.println("Base RemoteJobblettAcces URI: " + baseUri);


  } catch (Exception ioe) {
    fail(ioe.getMessage());
  }
}
/* TODO
@Test
  public void testController_initial() {
    assertNotNull(this.controller);
  }
  */



}