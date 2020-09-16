package jobblett.ui;

import jobblett.core.User;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

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
