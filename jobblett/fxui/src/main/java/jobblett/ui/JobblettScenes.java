package jobblett.ui;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public enum JobblettScenes {
  LOGIN("Login.fxml"),
  CREATE_USER("CreateUser.fxml"), 
  USER_HOME("UserHome.fxml"),
  JOIN_GROUP("JoinGroup.fxml"), 
  CREATE_GROUP("CreateGroup.fxml"),
  GROUP_HOME("GroupHome.fxml"), 
  SHIFT_VIEW("ShiftView.fxml"),
  UPDATE_SHIFT("UpdateShift.fxml");

  private final String filename;
  private SceneController controller;
  private Scene scene;

  JobblettScenes(String filename) {
    this.filename = filename;
    reset();
  }

  /**
   * TODO.
   */
  public void reset() {
    FXMLLoader loader = new FXMLLoader(App.class.getResource(filename));
    Parent parent = null;
    try {
      parent = loader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }
    Scene scene = new Scene(parent);
    SceneController sceneController = ((SceneController) loader.getController());
    sceneController.styleIt();
    this.controller = sceneController;
    this.scene = scene;
  }

  public String getFilename() {
    return filename;
  }

  public SceneController getController() {
    return controller;
  }

  public Scene getScene() {
    return scene;
  }

}
