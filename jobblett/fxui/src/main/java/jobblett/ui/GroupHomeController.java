package jobblett.ui;

import javafx.scene.control.Button;
import jobblett.core.User;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

public class GroupHomeController extends ScreenController{

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
    public void onScreenDisplayed() {
        // Sets GroupName on top of the screen
        groupName.setText(main.getActiveGroup().getGroupName());

        // Shows GroupID
        groupID.setText("GroupID: " + main.getActiveGroup().getGroupID());

        // Lists all members
        for (User user : main.getActiveGroup()) {
            Text text = new Text(user.toString());
            members.getItems().add(text);
        }
    }

    @FXML
    public void backButton(){
        mainController.setScreen(App.USER_HOME_ID);
    }
    
    @FXML
    public void viewShifts(){
        mainController.setScreen(App.SHIFT_VIEW_ID);
    }
}
