package jobblett.ui;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import jobblett.core.Group;
import jobblett.core.User;

public class UserHomeController extends SceneController {

    @FXML
    ListView<Group> groups;

    @FXML
    Text userFullName;

    @FXML
    Button logOutButton;

    @FXML
    Button createGroupButton;

    @FXML
    Button joinGroupButton;

    @Override
    public void onSceneDisplayed() {
        // Sets full name on top of the screen
        User activeUser = mainController.getActiveUser();
        String givenName = activeUser.getGivenName();
        String familyName = activeUser.getFamilyName();
        userFullName.setText(givenName + " " + familyName);

        groups.setCellFactory(groups -> {
          GroupListCell listCell = new GroupListCell(mainController);
          return listCell;
        });

        groups.getItems().clear();
        // Lists all groups
        for (Group group : getAccess().getGroups(mainController.getActiveUser())) {
            Text text = new Text(group.getGroupName());
            groups.getItems().add(text);
            text.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    mainController.setActiveGroup(group);
                    mainController.setScene(App.GROUP_HOME_ID);
                }
            });
        }
    }

    @FXML
    public void logOut() {
        mainController.setActiveUser(null);
        mainController.setScene(App.LOGIN_ID);
    }

    @FXML
    public void createGroup(){
    	mainController.setScene(App.CREATE_GROUP_ID);
    }

    @FXML
    public void joinGroup(){
      mainController.setScene(App.JOIN_GROUP_ID);
    }
}
