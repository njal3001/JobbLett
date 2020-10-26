package jobblett.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import jobblett.core.Group;

public class JoinGroupController extends SceneController {

  @FXML
  Button joinGroupButton;
  @FXML
  TextField groupIdField;
  @FXML
  Text errorMessage;
  @FXML
  Button goBackButton;

  @Override
  public void onSceneDisplayed() {
    // Sets a listener to prevent non-integers on groupID
    groupIdField.textProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue.length() != 0) {
          try {
            int i = Integer.parseInt(newValue);
            if (i >= 10000)
              groupIdField.setText(oldValue);
          } catch (NumberFormatException e) {
            groupIdField.setText(oldValue);
          }
        }
    });
  }

  @FXML
  public void goToUserHome(){
    mainController.setScene(App.USER_HOME_ID);
  }

  @FXML
  public void joinGroup(){
    int groupID = 0;

    //Disse error meldingene kan sikkert hentes direkte fra core
    try {
      groupID = Integer.parseInt(groupIdField.getText());
    } catch(NumberFormatException e){
      errorMessage.setText("Invalid group ID");
      return;
    }
    Group group = getAccess().getGroup(groupID);
    if (group == null) {
      errorMessage.setText("No group has the given ID");
      return;
    }
    try{
      group.addUser(mainController.getActiveUser());
      mainController.setActiveGroup(group);
      mainController.setScene(App.GROUP_HOME_ID);
    } catch(Exception e){
      errorMessage.setText(e.getMessage());
    }
  }
}
