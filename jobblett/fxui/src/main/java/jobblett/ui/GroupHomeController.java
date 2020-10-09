package jobblett.ui;

import javafx.scene.control.Button;
import jobblett.core.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import java.io.IOException;

public class GroupHomeController extends AbstractController{

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

    //Må fikse teksten på FXML
    // Vi burde gjøre noe for å generalisere listView koden, nå skriver vi ca. samme kode hver gang vi skal vise noe med listView
    // Må legge til en måte at man kan se hvem som er admin

    @Override
    public void update() {
        // Sets GroupName on top of the screen
        groupName.setText(getActiveGroup().getGroupName());

        // Shows GroupID
        groupID.setText("GroupID: " + getActiveGroup().getGroupID());

        // Lists all members
        for (User user : getActiveGroup()) {
            Text text = new Text(user.toString());
            members.getItems().add(text);
        }
    }

    @FXML
    public void backButton() throws IOException {
        changeScreen(new FXMLLoader(getClass().getResource("UserHome.fxml")), backToHome, main);
    }
    
    @FXML
    public void viewShifts() throws IOException {
        changeScreen(new FXMLLoader(getClass().getResource("ShiftView.fxml")), goToShifts, main);
    }
}
