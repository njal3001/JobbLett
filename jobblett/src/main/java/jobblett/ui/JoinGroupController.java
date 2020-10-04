package jobblett.ui;

import java.io.IOException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
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

    // Fungerer ikke, vet ikke hvorfor, heller ingen melding hvis man skriver inn feil kode
    @FXML
    public void initalize(){
        // Sets a listener to prevent non-integers on groupID
        groupIdField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if (!newValue.equals("")){
                    try {
                        int i = Integer.parseInt(newValue);
                        if (i>10000) groupIdField.setText(oldValue);
                    } catch (Exception e) {
                        groupIdField.setText(oldValue);
                    }
                }
            }
        });
    }

    @FXML
    public void goToUserHome() throws IOException {
        changeScreen("UserHome.fxml", goBackButton, main);
    }

   @FXML
    public void joinGroup() throws IOException{
        int groupID = Integer.parseInt(groupIdField.getText());
        try{
            Group group = main.getGroupList().getGroup(groupID);
            group.addUser(main.getLoggedIn());
            changeScreen("GroupHome.fxml", joinGroupButton, main);
        } catch(Exception e){
            errorMessage.setText(e.getMessage());
        }
        }
}
