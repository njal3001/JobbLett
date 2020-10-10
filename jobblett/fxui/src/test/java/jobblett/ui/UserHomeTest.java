package jobblett.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import org.testfx.framework.junit5.ApplicationTest;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import jobblett.core.Group;
import jobblett.core.Main;
import jobblett.core.User;

public class UserHomeTest extends ApplicationTest {

  // Må endre på testdata i forhold til json

  private UserHomeController controller;

  //Midlertidig?
  private Group group1;
  private Group group2;

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

  //Midlertidig, initalisering med json testdata skal implementeres
  private Main getMain(){
     User user = new User("CorrectUsername", "CorrectPassword12345", "Ole", "Dole");
     Main main = new Main();
     group1 = main.getGroupList().newGroup("Test Group 1");
     group2 = main.getGroupList().newGroup("Test Group 2");
     group2.addUser(user);
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
    tryToCreateGroup("Group Name");
    Text groupName = lookup("#groupName").query();
    assertEquals("Group Name", groupName.getText());
    clickOn("#backToHome");
    assertListViewHasItem("Group Name");
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

  // Får error, klarer ikke å finne ut hvorfor
  @Test
  public void testJoinGroup_validGroupId(){
    tryToJoinGroup(String.valueOf(group1.getGroupID()));
    Text groupName = lookup("#groupName").query();
    assertEquals(group1.getGroupName(), groupName.getText());
    clickOn("#backToHome");
    assertListViewHasItem(group1.getGroupName());
  }

  @Test
  public void testJoinGroup_invalidGroupId(){
    tryToJoinGroup("0");
    assertErrorMessage("No group has the given ID");
  }

  private void assertListViewHasItem(String itemText){
    ListView<Text> groups = lookup("#groups").query();
    assertNotNull(groups.getItems().stream().filter(text -> text.getText().equals(itemText)).findFirst().orElse(null));
  }

  //The group ID TextField should only accept numbers
  @Test
  public void testJoinGroup_writeInvalidInput(){
    tryToJoinGroup("abcd");
    TextField groupIdField = lookup("#groupIdField").query();
    assertEquals(0, groupIdField.getText().length());
  }

  @Test
  public void testJoinGroup_alreadyPartOfGroup(){
    tryToJoinGroup(String.valueOf(group2.getGroupID()));
    assertErrorMessage("You are already a member of the group");
  }

  private void tryToJoinGroup(String groupId){
    clickOn("#joinGroupButton");
    clickOn("#groupIdField").write(groupId);
    clickOn("#joinGroupButton");
  }

  @Test
  public void testGoToGroup(){
    
  }

}