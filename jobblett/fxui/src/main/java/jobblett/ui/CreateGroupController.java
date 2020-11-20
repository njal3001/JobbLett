package jobblett.ui;

import static jobblett.ui.JobblettScenes.GROUP_HOME;
import static jobblett.ui.JobblettScenes.USER_HOME;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CreateGroupController extends SceneController {

  @FXML Button createGroupButton;
  @FXML TextField groupNameField;
  @FXML Label errorMessage;
  @FXML Button goBackButton;

  public void styleIt() {
    createGroupButton.setSkin(new ButtonAnimationSkin(createGroupButton));
    goBackButton.setSkin(new ButtonAnimationSkin(goBackButton));
  }

  @FXML public void goToUserHome() {
    switchScene(USER_HOME);
  }

  /**
   * TODO.
   */
  @FXML public void createGroup() {
    String groupName = groupNameField.getText();
    try {
      int groupId = getAccess().newGroup(groupName);
      getAccess().addGroupMember(groupId, getActiveUsername());
      // The first member to join the group becomes admin
      getAccess().addGroupAdmin(groupId, getActiveUsername());
      setActiveGroupId(groupId);
      switchScene(GROUP_HOME);
    } catch (Exception e) {
      errorMessage.setText(e.getMessage());
    }
  }
}
