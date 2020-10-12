package jobblett.ui;

import java.io.IOException;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import jobblett.core.Group;

public class UserHomeController extends AbstractController {

    // UI for listen med gruppene er ødelagt, må fikse den
    @FXML
    ListView<Text> groups;

    @FXML
    Text userFullName;

    @FXML
    Button logOutButton;

    @FXML
    Button createGroupButton;

    @FXML
    Button joinGroupButton;

    @Override
    protected void update() {
        // Sets full name on top of the screen
        String givenName = getLoggedIn().getGivenName();
        String familyName = getLoggedIn().getFamilyName();
        userFullName.setText(givenName + " " + familyName);

        // Lists all groups
        for (Group group : main.getGroupList().getGroups(getLoggedIn())) {
            Text text = new Text(group.getGroupName());
            groups.getItems().add(text);
            text.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    main.setActiveGroup(group);
                    try {
                        changeScreen(new FXMLLoader(getClass().getResource("GroupHome.fxml")), text, main);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @FXML
    public void logOut() {
        main.logOut();
        try {
            changeScreen(new FXMLLoader(getClass().getResource("Login.fxml")), logOutButton, main);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void createGroup() throws IOException {
    	changeScreen(new FXMLLoader(getClass().getResource("CreateGroup.fxml")), createGroupButton, main);
    }

    @FXML
    public void joinGroup() throws IOException {
    	changeScreen(new FXMLLoader(getClass().getResource("JoinGroup.fxml")), joinGroupButton, main);
    }
}
