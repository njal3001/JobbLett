package jobblett.ui;

import static jobblett.ui.JobblettScenes.GROUP_HOME;
import static jobblett.ui.JobblettScenes.JOIN_GROUP;
import static jobblett.ui.JobblettScenes.USER_HOME;
import static org.junit.jupiter.api.Assertions.assertEquals;

import javafx.scene.control.TextField;
import jobblett.core.GroupMemberList;
import jobblett.core.User;
import org.junit.jupiter.api.Test;

public class JoinGroupControllerTest extends JobbLettTest {

  @Override public JobblettScenes giveId() {
    return JOIN_GROUP;
  }

  @Override public User optionalActiveUser() {
    return user1;
  }

  @Test public void testJoinGroup_validGroupId() {
    tryToJoinGroup(String.valueOf(group2.getGroupId()));
    uiAssertions.assertOnScene(GROUP_HOME);
    uiAssertions.assertLabel("groupName", group2.getGroupName());
    clickOn("#backToHome");
    uiAssertions.assertOnScene(USER_HOME);
    uiAssertions.assertListViewHasItem("groups", group2.getGroupId());
  }

  @Test public void testJoinGroup_invalidGroupId() {
    tryToJoinGroup("0");
    uiAssertions.assertOnScene(JOIN_GROUP);
    uiAssertions.assertLabel("errorMessage", "No group has the given ID");
  }

  //The group ID TextField should only accept numbers of maximum length 4
  @Test public void testJoinGroup_writeInvalidInput() {
    clickOn("#groupIdField").write("a");
    TextField groupIdField = lookup("#groupIdField").query();
    assertEquals(0, groupIdField.getText().length());
    clickOn("#groupIdField").write("100000");
    assertEquals(4, groupIdField.getText().length());
  }

  @Test public void testJoinGroup_alreadyPartOfGroup() {
    tryToJoinGroup(String.valueOf(group1.getGroupId()));
    uiAssertions.assertOnScene(JOIN_GROUP);
    uiAssertions.assertLabel("errorMessage", GroupMemberList.ALREADY_EXIST_ERROR_TEXT);
  }

  private void tryToJoinGroup(String groupId) {
    clickOn("#groupIdField").write(groupId);
    clickOn("#joinGroupButton");
  }
}
