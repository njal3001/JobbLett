package jobblett.ui;

import java.io.IOException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import jobblett.core.Group;
import jobblett.core.User;

public class UserHomeController extends AbstractController{

    @FXML
    ListView<Text> groups;

    @FXML
    Text userFullName;

    @FXML
    Button logOut;

    @FXML
    Pane noExistingGroups;

    @FXML
    Pane hasExistingGroups;

    @FXML
    TextField groupID;

    @FXML
    Button link;

    public void update() {
        // Sets full name on top of the screen
        String givenName = activeUser.getGivenName();
        String familyName = activeUser.getFamilyName();
        userFullName.setText(givenName + " " + familyName);

        // Switches to right state
        if (main.getGroupList().getGroups(activeUser).isEmpty()) 
            switchToNoExistingGroups();
        else 
            switchToHasExistingGroups();

        // Lists all groups
        for (Group group : main.getGroupList().getGroups(activeUser)) {
         //   String groupName = group.getGroupName();
            Text text = new Text(group.getGroupName());
            groups.getItems().add(text);
            text.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    main.setActiveGroup(group);
                    try {
                        changeScreen("GroupHome.fxml",text, main);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        // Sets a listener to prevent non-integers on groupID
        groupID.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if (!newValue.equals("")){
                    try {
                        int i = Integer.parseInt(newValue);
                        if (i>10000) groupID.setText(oldValue);
                    } catch (Exception e) {
                        groupID.setText(oldValue);
                    }
                }
            }
        });
    }

    @FXML
    public void logOut() {
        main.logOut();
        try {
            changeScreen("Login.fxml",logOut, main);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void linkGroup(){
        int groupID = Integer.parseInt(this.groupID.getText());
        Group group = main.getGroupList().getGroup(groupID);
        group.addUser(activeUser);
        main.setActiveGroup(group);
        try {
            changeScreen("GroupHome.fxml", link, main);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void switchToNoExistingGroups() {
        hasExistingGroups.setDisable(true);
        hasExistingGroups.setVisible(false);
        noExistingGroups.setDisable(false);
        noExistingGroups.setVisible(true);
    }

    private void switchToHasExistingGroups() {
        noExistingGroups.setDisable(true);
        noExistingGroups.setVisible(false);
        hasExistingGroups.setDisable(false);
        hasExistingGroups.setVisible(true);
    }


}
