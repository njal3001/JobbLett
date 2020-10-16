package jobblett.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import jobblett.core.JobShift;

public class ShiftViewController extends SceneController{
    
    @FXML
    Text groupName;

    @FXML
    ListView<Text> shifts;

    @FXML
    Text groupID;

    @FXML
    Button backToGroup;

    @FXML
    Text shiftsText;

    @FXML 
    Button newShiftButton;


    @Override
    public void onSceneDisplayed() {
        // Sets GroupName on top of the screen
        groupName.setText(main.getActiveGroup().getGroupName());

        // Shows GroupID
        groupID.setText("GroupID: " + main.getActiveGroup().getGroupID());

        shifts.getItems().clear();
       // Lists all members
        for (JobShift shift : main.getActiveGroup().getJobShifts()) {
            Text text = new Text(shift.toString());
            shifts.getItems().add(text);
        }
        newShiftButton.setVisible(main.getActiveGroup().isAdmin(main.getLoggedIn()));
    }

    @FXML
    public void backButton(){
        mainController.setScene(App.GROUP_HOME_ID);
    }

    @FXML
    public void goToCreateShift(){
      mainController.setScene(App.CREATE_SHIFT_ID);
    }
}
