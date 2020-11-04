package jobblett.ui;

import static jobblett.ui.JobblettScenes.SHIFT_VIEW_ID;
import static jobblett.ui.JobblettScenes.USER_HOME_ID;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import jobblett.core.User;

public class GroupHomeController extends SceneController {

  @FXML Label groupName;

  @FXML ListView<User> members;

  @FXML Label groupId;

  @FXML Button backToHome;

  @FXML Button goToShifts;


  /**
   * TODO.
   */
  @FXML public void initialize() {
    members.setCellFactory(members -> new GroupMemberListCell());
    //Sets the ListView uninteractable
    members.setMouseTransparent(true);
    //members.setFocusTraversable(false);
  }

  @Override public void styleIt() {
    super.styleIt();
    backToHome.setSkin(new ButtonAnimationSkin(backToHome));
  }

  @Override public void onSceneDisplayed() {
    // Sets GroupName on top of the screen
    groupName.setText(getActiveGroup().getGroupName());

    // Shows GroupID
    groupId.setText("GroupID: " + getActiveGroup().getGroupId());

    members.getItems().clear();
    // Lists all members
    for (User user : getActiveGroup()) {
      members.getItems().add(user);
    }
  }

  @FXML public void backButton() {
    switchScene(USER_HOME_ID);
  }

  @FXML public void viewShifts() {
    switchScene(SHIFT_VIEW_ID);
  }
}
