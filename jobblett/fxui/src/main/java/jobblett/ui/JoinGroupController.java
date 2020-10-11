package jobblett.ui;

import java.io.IOException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import jobblett.core.Group;

public class JoinGroupController extends AbstractController {

  @FXML
  Button joinGroupButton;
  @FXML
  TextField groupIdField;
  @FXML
  Text errorMessage;
  @FXML
  Button goBackButton;

  // Fungerer ikke, vet ikke hvorfor
  @Override
  public void update() {
    // Sets a listener to prevent non-integers on groupID
    groupIdField.textProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue.length() != 0) {
          try {
            int i = Integer.parseInt(newValue);
            if (i >= 10000)
              groupIdField.setText(oldValue);
          } catch (NumberFormatException e) {
            groupIdField.setText(oldValue);
          }
        }
    });
  }

  @FXML
  public void goToUserHome() throws IOException {
    changeScreen(new FXMLLoader(getClass().getResource("UserHome.fxml")), goBackButton, main);
  }

  @FXML
  public void joinGroup() throws IOException {
    int groupID = 0;
    try {
      groupID = Integer.parseInt(groupIdField.getText());
    } catch(NumberFormatException e){
      errorMessage.setText("Invalid group ID");
      return;
    }
    Group group = main.getGroupList().getGroup(groupID);
    if (group == null) {
      errorMessage.setText("No group has the given ID");
      return;
    }
    try{
      group.addUser(main.getLoggedIn());
      main.setActiveGroup(group);
      changeScreen(new FXMLLoader(getClass().getResource("GroupHome.fxml")), joinGroupButton, main);
    } catch(Exception e){
      errorMessage.setText(e.getMessage());
    }
  }
}
