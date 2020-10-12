package jobblett.ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import jobblett.core.Group;

public class CreateGroupController extends AbstractController {

    @FXML
    Button createGroupButton;
    @FXML
    TextField groupNameField;
    @FXML
    Text errorMessage;
    @FXML
    Button goBackButton;

    @FXML
    public void goToUserHome() throws IOException {
        changeScreen(new FXMLLoader(getClass().getResource("UserHome.fxml")), goBackButton, main);
    }

   @FXML
    public void createGroup(){
      errorMessage.setText("Message:hfg");
      System.out.println(errorMessage.getText());
        String groupName = groupNameField.getText();
        try{
            Group newGroup = main.getGroupList().newGroup(groupName);
            newGroup.addUser(getLoggedIn());
            main.setActiveGroup(newGroup);
            changeScreen(new FXMLLoader(getClass().getResource("GroupHome.fxml")), createGroupButton, main);
        } catch(Exception e){
          if(e.getMessage()==null){
            System.out.println("hewiewhireh");
          }
          System.out.println(e.getStackTrace());
            System.out.println(errorMessage.getText());
            errorMessage.setText("Message"+e.getMessage());
             System.out.println(errorMessage.getText());

            System.out.println(e.getMessage());
        }
        }


}
