package jobblett.ui;

import static jobblett.ui.JobblettScenes.CREATE_GROUP;
import static jobblett.ui.JobblettScenes.JOIN_GROUP;
import static jobblett.ui.JobblettScenes.LOGIN;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class UserHomeController extends SceneController {

  @FXML ListView<Integer> groups;

  @FXML Label userFullName;

  @FXML Button logOutButton;

  @FXML Button createGroupButton;

  @FXML Button joinGroupButton;

  @FXML public void initialize() {
    groups.setCellFactory(groups -> new GroupListCell(getControllerMap()));
  }

  //TODO bytte til initialize??
  @Override public void onSceneDisplayed() {
    // Sets full name on top of the screen
    userFullName.setText(getAccess().getUserFullName(getActiveUsername()));
    groups.getItems().clear();
    // Lists all groups
    for (int groupId : getAccess().getAllGroupIds(getActiveUsername())) {
      groups.getItems().add(groupId);
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
