package jobblett.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import jobblett.core.User;

public class GroupHomeController extends SceneController{

    @FXML
    Text groupName;

    @FXML
    ListView<Text> members;

    @FXML
    Text groupID;

    @FXML
    Button backToHome;
    
    @FXML
    Button goToShifts;

    // Vi burde gjøre noe for å generalisere listView koden, nå skriver vi ca. samme kode hver gang vi skal vise noe med listView
    // Må legge til en måte at man kan se hvem som er admin

    @Override
    public void onSceneDisplayed() {
        // Sets GroupName on top of the screen
        groupName.setText(mainController.getActiveGroup().getGroupName());

        // Shows GroupID
        groupID.setText("GroupID: " + mainController.getActiveGroup().getGroupID());

        members.getItems().clear();
        // Lists all members
        for (User user : mainController.getActiveGroup()) {
            Text text = new Text(user.toString());
            members.getItems().add(text);
        }
    }

    @FXML
    public void backButton(){
        mainController.setScene(App.USER_HOME_ID);
    }
    
    @FXML
    public void viewShifts(){
        mainController.setScene(App.SHIFT_VIEW_ID);
    }
}
