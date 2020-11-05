package jobblett.ui;

import static jobblett.ui.JobblettScenes.GROUP_HOME;
import static jobblett.ui.JobblettScenes.USER_HOME;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import jobblett.core.Group;

public class CreateGroupController extends SceneController {

  @FXML Button createGroupButton;
  @FXML TextField groupNameField;
  @FXML Label errorMessage;
  @FXML Button goBackButton;

  public void styleIt() {
    createGroupButton.setSkin(new ButtonAnimationSkin(createGroupButton));
    goBackButton.setSkin(new ButtonAnimationSkin(goBackButton));
  }

  /**
   * TODO.
   */
  @FXML public void goToUserHome() {
    switchScene(USER_HOME);
  }

  /**
   * TODO.
   */
  @FXML public void createGroup() {
    String groupName = groupNameField.getText();
    try {
      Group newGroup = getAccess().newGroup(groupName);
      newGroup.addUser(getActiveUser());
      // The first member to join the group becomes admin
      newGroup.addAdmin(getActiveUser());
      setActiveGroup(newGroup);
      switchScene(GROUP_HOME);
    } catch (Exception e) {
      errorMessage.setText(e.getMessage());
    }
  }
}
