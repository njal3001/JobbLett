package jobblett.ui;

import static jobblett.ui.JobblettScenes.CREATE_GROUP;
import static jobblett.ui.JobblettScenes.JOIN_GROUP;
import static jobblett.ui.JobblettScenes.LOGIN;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import jobblett.core.Group;
import jobblett.core.User;

public class UserHomeController extends SceneController {

  @FXML ListView<Integer> groupNames;

  @FXML Label userFullName;

  @FXML Button logOutButton;

  @FXML Button createGroupButton;

  @FXML Button joinGroupButton;

  @FXML public void initialize() {
    groupNames.setCellFactory(groups -> new GroupListCell(getControllerMap()));
  }

  @Override public void onSceneDisplayed() {
    // Sets full name on top of the screen
    userFullName.setText(getAccess().getUserFullName(getActiveUsername()));
    groupNames.getItems().clear();
    // Lists all groups
    for (int groupId : getAccess().getAllGroupIds(getActiveUsername())) {
      groupNames.getItems().add(groupId);
    }
  }

  @FXML public void logOut() {
    setActiveUsername(null);
    switchScene(LOGIN);
  }

  @FXML public void createGroup() {
    switchScene(CREATE_GROUP);
  }

  @FXML public void joinGroup() {
    switchScene(JOIN_GROUP);
  }

  @Override public void styleIt() {
    super.styleIt();
    logOutButton.setSkin(new ButtonAnimationSkin(logOutButton));
    createGroupButton.setSkin(new ButtonAnimationSkin(createGroupButton));
    joinGroupButton.setSkin(new ButtonAnimationSkin(joinGroupButton));
  }
}
