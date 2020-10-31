package jobblett.ui;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jobblett.core.*;

//Code is inspired by: https://github.com/acaicedo/JFX-MultiScreen/tree/master/ScreensFramework/src/screensframework

//This controller changes the screen in the app
public class MainController {

  private Stage stage;

  private Map<String, Scene> scenes = new HashMap<String, Scene>();
  private Map<String, SceneController> sceneControllers = new HashMap<String, SceneController>();

  private User activeUser;
  private Group activeGroup;

  JobblettAccess access;

  public Stage getStage(){
    return stage;
  }

  public MainController(Stage stage) {
    this.stage = stage;
    if (App.REST_API_ON) {
      try {
        access = new JobblettRemoteAccess(new URI(""));// Has to be updated to the right URI
      } catch (URISyntaxException e) {
        e.printStackTrace();
      }
    }
    else access = new JobblettDirectAccess();

  }


  public void setActiveUser(User activeUser) {
    this.activeUser = activeUser;
  }

  public User getActiveUser() {
    return activeUser;
  }

  public void setActiveGroup(Group activeGroup) {
    this.activeGroup = activeGroup;
  }

  public Group getActiveGroup() {
    return activeGroup;
  }

  public Scene getScene(String name) {
    return scenes.get(name);
  }

  public void loadScene(String name, String resource) throws IOException {
      FXMLLoader loader = new FXMLLoader(getClass().getResource(resource));
      Parent parent = loader.load();
      Scene scene = new Scene(parent);
      SceneController sceneController = ((SceneController) loader.getController());
      sceneController.setMainController(this);
      scenes.put(name, scene);
      sceneControllers.put(name, sceneController);
  }

  public void setScene(final String name) {
    if (scenes.get(name) != null){
      stage.setScene(scenes.get(name));
      getSceneController(name).onSceneDisplayed();
    }
  }

  public void unloadScreen(String name) {
    scenes.remove(name);
    sceneControllers.remove(name);
  }

  public SceneController getSceneController(String name) {
    return sceneControllers.get(name);
  }
}