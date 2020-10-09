package jobblett.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;
import jobblett.core.Group;
import jobblett.core.Main;
import jobblett.core.User;

public class UserHomeTest extends ApplicationTest {

  // Må endre på testdata i forhold til json

  private UserHomeController controller;

  //Midlertidig?
  private Group group;

  @Override
  public void start(final Stage stage) throws Exception{
    FXMLLoader loader = new FXMLLoader(getClass().getResource("UserHome.fxml"));
    Parent root = loader.load();
    controller = loader.getController();
    stage.setScene(new Scene(root));
    stage.show();

    //Midlertidig
    controller.setMain(getMain());
  }

  //Midlertidig
  private Main getMain(){
     User user = new User("CorrectUsername", "CorrectPassword12345", "Ole", "Dole");
     Main main = new Main();
     group = main.getGroupList().newGroup("Test Group");
     main.getUserList().addUser(user);
     main.logIn(user);
     return main;
  }

  @Test
  public void testController(){
    assertNotNull(controller);
  }

  @Test
  public void testCreateGroup_validGroupName(){
    clickOn("#createGroupButton");
    clickOn("#groupNameField").write("Group Name");
    clickOn("#createGroupButton");
    Text groupName = lookup("#groupName").query();
    assertEquals("Group Name", groupName.getText());
    clickOn("#backToHome");
    ListView<Text> groups = lookup("#groups").query();
    assertNotNull(groups.getItems().stream().filter(text -> text.getText().equals("Group Name")).findFirst().orElse(null));
  }

  @Test
  public void testCreateGroup_invalidGroupName(){
    tryToCreateGroup("    ");
    assertErrorMessage("Group name must have at least 2 characters");
  }

  private void tryToCreateGroup(String groupName){
    clickOn("#createGroupButton");
    clickOn("#groupNameField").write(groupName);
    clickOn("#createGroupButton");
  }

  private void assertErrorMessage(String expected){
    Text errorMessage = lookup("#errorMessage").query();
    assertEquals(expected, errorMessage.getText());
  }

  @Test
  public void testJoinGroup_validGroupId(){
    clickOn("#joinGroupButton");
    clickOn("#groupIdField").write(String.valueOf(group.getGroupID()));
    clickOn("#joinGroupButton");
    Text groupName = lookup("#groupName").query();
    assertEquals(group.getGroupName(), groupName.getText());
    clickOn("#backToHome");
    ListView<Text> groups = lookup("#groups").query();
    assertNotNull(groups.getItems().stream().filter(text -> text.getText().equals(group.getGroupName())).findFirst().orElse(null));
  }

  @Test
  public void testJoinGroup_invalidGroupId(){
    tryToJoinGroup("0");
    assertErrorMessage("No group has the given ID");
  }

  @Test
  public void testJoinGroup_writeNonIntegers(){
    tryToJoinGroup("abcd");
    TextField groupIdField = lookup("#groupIdField").query();
    assertEquals(0, groupIdField.getText().length());
  }

  @Test
  public void testJoinGroup_alreadyPartOfGroup(){
    tryToJoinGroup("abcd");

  }

  private void tryToJoinGroup(String groupId){
    clickOn("#joinGroupButton");
    clickOn("#groupIdField").write(groupId);
    clickOn("#joinGroupButton");
  }


}