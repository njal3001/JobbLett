package jobblett.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import jobblett.core.Group;

public class CreateGroupController extends SceneController {

  @FXML
  Button createGroupButton;
  @FXML
  TextField groupNameField;
  @FXML
  Text errorMessage;
  @FXML
  Button goBackButton;

  @Override
  public void onSceneDisplayed() {
    groupNameField.setText("");
    errorMessage.setText("");
  }

  @FXML
  public void goToUserHome() {
    mainController.setScene(App.USER_HOME_ID);
  }

  @FXML
  public void createGroup(){
    String groupName = groupNameField.getText();
    try {
      Group newGroup = getAccess().newGroup(groupName);
      newGroup.addUser(mainController.getActiveUser());
      // The first member to join the group becomes admin
      newGroup.addAdmin(mainController.getActiveUser());
      mainController.setActiveGroup(newGroup);
      mainController.setScene(App.GROUP_HOME_ID);
    } catch (Exception e) {
      errorMessage.setText(e.getMessage());
    }
  }
}
