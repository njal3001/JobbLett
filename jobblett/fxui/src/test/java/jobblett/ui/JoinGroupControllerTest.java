package jobblett.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import jobblett.core.GroupMemberList;
import org.junit.jupiter.api.Test;

import javafx.scene.control.TextField;
import jobblett.core.Group;
import jobblett.core.User;

public class JoinGroupControllerTest extends JobbLettTest {

  @Override
  protected String giveID() {
    return App.JOIN_GROUP_ID;
  }

  @Override
  protected User giveActiveUser(){
    return user1;
  }

  @Override
  protected Group giveActiveGroup(){
    return null;
  }

  @Test
  public void testJoinGroup_validGroupId() {
    tryToJoinGroup(String.valueOf(group2.getGroupID()));
    uiAssertions.assertOnScene(App.GROUP_HOME_ID);
    uiAssertions.assertLabel("groupName", group2.getGroupName());
    clickOn("#backToHome");
    uiAssertions.assertOnScene(App.USER_HOME_ID);
    uiAssertions.assertListViewHasItem("groups", group2);
  } 

  @Test
  public void testJoinGroup_invalidGroupId(){
    tryToJoinGroup("0");
    uiAssertions.assertOnScene(App.JOIN_GROUP_ID);
    uiAssertions.assertLabel("errorMessage", "No group has the given ID");
  }

  //The group ID TextField should only accept numbers of maximum length 4
  @Test
  public void testJoinGroup_writeInvalidInput(){
    clickOn("#groupIdField").write("a");
    TextField groupIdField = lookup("#groupIdField").query();
    assertEquals(0, groupIdField.getText().length());
    clickOn("#groupIdField").write("100000");
    assertEquals(4, groupIdField.getText().length());
  }

  @Test
  public void testJoinGroup_alreadyPartOfGroup(){
    tryToJoinGroup(String.valueOf(group1.getGroupID()));
    uiAssertions.assertOnScene(App.JOIN_GROUP_ID);
    uiAssertions.assertLabel("errorMessage", GroupMemberList.ALREADY_EXIST_ERROR_TEXT);
  } 

  private void tryToJoinGroup(String groupId){
    clickOn("#groupIdField").write(groupId);
    clickOn("#joinGroupButton");
  }
}