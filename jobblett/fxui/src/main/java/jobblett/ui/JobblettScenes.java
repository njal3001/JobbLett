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

  JobblettScenes(String filename) {
    this.filename = filename;
  }

  public String getFilename() {
    return filename;
  }


}
