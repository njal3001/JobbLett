package jobblett.ui;

import static jobblett.ui.JobblettScenes.CREATE_GROUP_ID;
import static jobblett.ui.JobblettScenes.JOIN_GROUP_ID;
import static jobblett.ui.JobblettScenes.LOGIN_ID;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import jobblett.core.Group;
import jobblett.core.User;

public class UserHomeController extends SceneController {

  @FXML ListView<Group> groups;

  @FXML Label userFullName;

  @FXML Button logOutButton;

  @FXML Button createGroupButton;

  @FXML Button joinGroupButton;



  @FXML public void initialize() {
    groups.setCellFactory(groups -> new GroupListCell());
  }

  @Override public void onSceneDisplayed() {
    // Sets full name on top of the screen
    User activeUser = getActiveUser();
    String givenName = activeUser.getGivenName();
    String familyName = activeUser.getFamilyName();
    userFullName.setText(givenName + " " + familyName);
    groups.getItems().clear();
    // Lists all groups
    for (Group group : getAccess().getGroups(getActiveUser())) {
      groups.getItems().add(group);
    }
  }

  @FXML public void logOut() {
    setActiveUser(null);
    switchScene(LOGIN_ID);
  }

  @FXML public void createGroup() {
    switchScene(CREATE_GROUP_ID);
  }

  @FXML public void joinGroup() {
    switchScene(JOIN_GROUP_ID);
  }

  @Override public void styleIt() {
    super.styleIt();
    logOutButton.setSkin(new ButtonAnimationSkin(logOutButton));
    createGroupButton.setSkin(new ButtonAnimationSkin(createGroupButton));
    joinGroupButton.setSkin(new ButtonAnimationSkin(joinGroupButton));
  }
}
