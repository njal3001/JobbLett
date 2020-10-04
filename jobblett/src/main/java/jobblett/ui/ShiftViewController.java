package jobblett.ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import jobblett.core.JobShift;

public class ShiftViewController extends AbstractController{
    
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
    public void update() {
        // Sets GroupName on top of the screen
        groupName.setText(activeGroup.getGroupName());

        // Shows GroupID
        groupID.setText("GroupID: " + activeGroup.getGroupID());

       // Lists all members
        for (JobShift shift : activeGroup.getJobShifts()) {
            Text text = new Text(shift.toString());
            shifts.getItems().add(text);
        }
    }

    @FXML
    public void backButton() throws IOException {
        changeScreen("GroupHome.fxml", backToGroup, main);
    }

    @FXML
    public void goToCreateShift() throws IOException {
        changeScreen("CreateShift.fxml", newShiftButton, main);
    }
    
    
}
