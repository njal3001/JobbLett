package jobblett.ui;

import static jobblett.ui.JobblettScenes.SHIFT_VIEW;
import static jobblett.ui.JobblettScenes.USER_HOME;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import jobblett.core.User;

public class GroupHomeController extends SceneController {

  //TODO: burde oppdatere navn

  @FXML Label groupName;

  @FXML ListView<String> membersInfo;

  @FXML Label groupId;

  @FXML Button backToHome;

  @FXML Button goToShifts;


  /**
   * TODO.
   */
  @FXML public void initialize() {
    membersInfo.setCellFactory(members -> new GroupMemberListCell(getControllerMap()));
    //Sets the ListView uninteractable with the mouse and the keyboard
    membersInfo.setMouseTransparent(true);
    membersInfo.setFocusTraversable(false);
  }

  @Override public void styleIt() {
    super.styleIt();
    backToHome.setSkin(new ButtonAnimationSkin(backToHome));
  }

  @Override public void onSceneDisplayed() {
    // Sets GroupName on top of the screen
    groupName.setText(getAccess().getGroupName(getActiveGroupId()));

    // Shows GroupID
    groupId.setText("GroupID: " + getActiveGroupId());

    membersInfo.getItems().clear();
    // Lists all members
    for (String username : getAccess().getGroupUsernames(getActiveGroupId())) {
      membersInfo.getItems().add(username);
    }
  }

  @FXML public void backButton() {
    switchScene(USER_HOME);
  }

  @FXML public void viewShifts() {
    switchScene(SHIFT_VIEW);
  }
}
