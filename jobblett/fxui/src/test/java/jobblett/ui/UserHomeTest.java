package jobblett.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class UserHomeTest extends JobbLettTest {

  @Override
  public void start(final Stage stage) throws Exception {
    fxmlFileName = "UserHome.fxml";
    super.start(stage);
  }

  @Override
  protected void setupData() {
    super.setupData();
    main.logIn(user1);
  }

  @Test
  public void testCreateGroup_validGroupName() {
    tryToCreateGroup("Group Name");
    Text groupName = lookup("#groupName").query();
    assertEquals("Group Name", groupName.getText());
    clickOn("#backToHome");
    assertListViewHasItem("Group Name");
  }

  @Test
  public void testCreateGroup_invalidGroupName() {
    tryToCreateGroup("    ");
    assertErrorMessage("Group name must have at least 2 characters");
  }

  private void tryToCreateGroup(String groupName) {
    clickOn("#createGroupButton");
    clickOn("#groupNameField").write(groupName);
    clickOn("#createGroupButton");
  }

  private void assertErrorMessage(String expected) {
    Text errorMessage = lookup("#errorMessage").query();
    assertEquals(expected, errorMessage.getText());
  }

  @Test
  public void testJoinGroup_validGroupId() {
    tryToJoinGroup(String.valueOf(group2.getGroupID()));
    Text groupName = lookup("#groupName").query();
    assertEquals(group2.getGroupName(), groupName.getText());
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

  //The group ID TextField should only accept numbers of maximum length 4
  @Test
  public void testJoinGroup_writeInvalidInput(){
    tryToJoinGroup("a");
    TextField groupIdField = lookup("#groupIdField").query();
    assertEquals(0, groupIdField.getText().length());
    assertErrorMessage("Invalid group ID");
    clickOn("#groupIdField").write("100000");
    assertEquals(4, groupIdField.getText().length());
  }

  @Test
  public void testJoinGroup_alreadyPartOfGroup(){
    tryToJoinGroup(String.valueOf(group1.getGroupID()));
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