package jobblett.ui;

import static jobblett.ui.JobblettScenes.GROUP_HOME_ID;

import jobblett.core.Group;
import jobblett.core.User;
import org.junit.jupiter.api.Test;

public class GroupHomeTest extends JobbLettTest {

  @Override protected JobblettScenes giveID() {
    return GROUP_HOME_ID;
  }

  @Override protected User giveActiveUser() {
    return user1;
  }

  @Override protected Group giveActiveGroup() {
    return group1;
  }

  @Test public void testMembersShowingInView() {
    uiAssertions.assertListViewHasItem("members", user1);
    uiAssertions.assertListViewHasItem("members", user2);
  }

  @Test public void testCorrectGroupId() {
    uiAssertions.assertLabel("groupId", "GroupID: " + group1.getGroupId());
  }

  //TODO To be implemented
  @Test public void testAdminVisibility() {

  }
}
