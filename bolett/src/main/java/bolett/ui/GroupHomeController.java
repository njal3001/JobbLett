package bolett.ui;

import bolett.core.Group;
import bolett.core.User;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.io.IOException;

public class GroupHomeController extends AbstractController{

    @FXML
    Text groupName;

    @FXML
    ListView members;

    @FXML
    Text groupID;

    @FXML
    public void initialize() {
        // Sets GroupName on top of the screen
        groupName.setText(activeGroup.getGroupName());

        // Shows GroupID
        groupID.setText("GroupID: "+activeGroup.getGroupID());

        // Lists all members
        for (User user : activeGroup) {
            Text text = new Text(user.toString());
            members.getItems().add(text);
        }
    }


}
