package jobblett.ui;

import static jobblett.ui.JobblettScenes.GROUP_HOME;
import static jobblett.ui.JobblettScenes.USER_HOME;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import jobblett.core.Group;

public class JoinGroupController extends SceneController {

  @FXML Button joinGroupButton;
  @FXML TextField groupIdField;
  @FXML Label errorMessage;
  @FXML Button goBackButton;

  /**
   * TODO.
   */
  @FXML public void initialize() {
    // Sets a listener to prevent non-integers on groupID
    groupIdField.textProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue.length() != 0) {
        try {
          int i = Integer.parseInt(newValue);
          if (i >= 10000) {
            groupIdField.setText(oldValue);
          }
        } catch (NumberFormatException e) {
          groupIdField.setText(oldValue);
        }
      }
    });
  }

  @Override public void styleIt() {
    super.styleIt();
    goBackButton.setSkin(new ButtonAnimationSkin(goBackButton));
    joinGroupButton.setSkin(new ButtonAnimationSkin(joinGroupButton));
  }

  @Override public void onSceneDisplayed() {
    groupIdField.setText("");
    errorMessage.setText("");
  }

  @FXML public void goToUserHome() {
    switchScene(USER_HOME);
  }

  /**
   * TODO.
   */
  @FXML public void joinGroup() {
    int groupId = 0;

    // Disse error meldingene kan sikkert hentes direkte fra core
    try {
      groupId = Integer.parseInt(groupIdField.getText());
    } catch (NumberFormatException e) {
      errorMessage.setText("Invalid group ID");
      return;
    }
    Group group = getAccess().getGroup(groupId);
    if (group == null) {
      errorMessage.setText("No group has the given ID");
      return;
    }
    try {
      group.addUser(getActiveUser());
      setActiveGroup(group);
      switchScene(GROUP_HOME);
    } catch (Exception e) {
      errorMessage.setText(e.getMessage());
    }
  }
}
