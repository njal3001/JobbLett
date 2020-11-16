package jobblett.ui;

import static jobblett.ui.JobblettScenes.SHIFT_VIEW;
import static jobblett.ui.JobblettScenes.USER_HOME;

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
   * Setting up the format for the listView of the members.
   * Makes the listView uninteractabl with the mouse and the keyboard.
   */
  @FXML public void initialize() {
    members.setCellFactory(members -> new GroupMemberListCell(getControllerMap()));
    members.setMouseTransparent(true);
    members.setFocusTraversable(false);
  }

  @Override public void styleIt() {
    super.styleIt();
    backToHome.setSkin(new ButtonAnimationSkin(backToHome));
  }
  //TODO kan vi ikke bare bruke intialize??
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
    switchScene(USER_HOME);
  }

  @FXML public void viewShifts() {
    switchScene(SHIFT_VIEW);
  }
}
