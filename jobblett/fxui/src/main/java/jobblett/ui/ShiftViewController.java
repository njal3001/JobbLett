package jobblett.ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
        groupName.setText(getActiveGroup().getGroupName());

        // Shows GroupID
        groupID.setText("GroupID: " + getActiveGroup().getGroupID());

       // Lists all members
        for (JobShift shift : getActiveGroup().getJobShifts()) {
            Text text = new Text(shift.toString());
            shifts.getItems().add(text);
        }
        newShiftButton.setVisible(getActiveGroup().isAdmin(getLoggedIn()));
    }

    @FXML
    public void backButton() throws IOException {
        changeScreen(new FXMLLoader(getClass().getResource("GroupHome.fxml")), backToGroup, main);
    }

    @FXML
    public void goToCreateShift() throws IOException {
        changeScreen(new FXMLLoader(getClass().getResource("CreateShift.fxml")), newShiftButton, main);
    }
    
    
}
