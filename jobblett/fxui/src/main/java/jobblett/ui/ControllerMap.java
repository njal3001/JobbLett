package jobblett.ui;

import java.io.IOException;
import java.util.HashMap;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

//Code is inspired by: https://github.com/acaicedo/JFX-MultiScreen/tree/master/ScreensFramework/src/screensframework

public class ControllerMap {

  private HashMap<JobblettScenes, Scene> map = new HashMap<>();
  private HashMap<JobblettScenes, SceneController> controllerMap = new HashMap<>();

  // Common fields
  private String activeUsername;
  private int activeGroupId;
  private Stage stage;
  private WorkspaceAccess access;

  /**
   * Creates a instance of ControllerMap.
   *
   * @param stage the stage used to load fxml-files and scenes
   * @param access used to give access for the controllers
   */
  public ControllerMap(Stage stage, WorkspaceAccess access) {
    this.stage = stage;
    this.access = access;
    for (JobblettScenes jobblettScenes : JobblettScenes.values()) {
      reset(jobblettScenes);
    }
  }

  public Stage getStage() {
    return stage;
  }

  public WorkspaceAccess getAccess() {
    return access;
  }

  public String getActiveUsername() {
    return activeUsername;
  }

  public void setActiveUsername(String activeUsername) {
    this.activeUsername = activeUsername;
  }

  public int getActiveGroupId() {
    return activeGroupId;
  }

  public void setActiveGroupId(int activeGroupId) {
    this.activeGroupId = activeGroupId;
  }

  public Scene getScene(JobblettScenes jobblettScenes) {
    return map.get(jobblettScenes);
  }

  private void setScene(JobblettScenes jobblettScenes, Scene scene) {
    map.put(jobblettScenes, scene);
  }

  public SceneController getController(JobblettScenes jobblettScenes) {
    return controllerMap.get(jobblettScenes);
  }

  private void setController(JobblettScenes jobblettScenes, SceneController controller) {
    controllerMap.put(jobblettScenes, controller);
  }

  private void reset(JobblettScenes jobblettScenes) {
    FXMLLoader loader = new FXMLLoader(App.class.getResource(jobblettScenes.getFilename()));
    Parent parent = null;
    try {
      parent = loader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }
    Scene scene = new Scene(parent);
    SceneController sceneController = ((SceneController) loader.getController());
    sceneController.styleIt();
    setController(jobblettScenes, sceneController);
    setScene(jobblettScenes, scene);
    getController(jobblettScenes).setControllerMap(this);
  }

  /**
   * Switches to given scene.
   *
   * @param jobblettScenes the given scene
   */
  public void switchScene(final JobblettScenes jobblettScenes) {
    reset(jobblettScenes);
    stage.setScene(getScene(jobblettScenes));
    getController(jobblettScenes).onSceneDisplayed();
  }
}
