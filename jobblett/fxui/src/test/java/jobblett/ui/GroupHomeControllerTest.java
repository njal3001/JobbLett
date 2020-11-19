package jobblett.ui;

import static jobblett.ui.JobblettScenes.GROUP_HOME;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import java.util.Collection;

import jobblett.core.Group;
import jobblett.core.User;
import org.junit.jupiter.api.Test;

import static jobblett.ui.JobblettScenes.USER_HOME;
import static jobblett.ui.JobblettScenes.SHIFT_VIEW;

public class GroupHomeControllerTest extends JobbLettTest {

  @Override public JobblettScenes giveId() {
    return GROUP_HOME;
  }

  @Override public User optionalActiveUser() {
    return user1;
  }

  @Override public Group optionalActiveGroup() {
    return group1;
  }

  @Test
  public void testMembersShowingInView() {
    uiAssertions.assertListViewHasItem("members", user1.getUsername());
    uiAssertions.assertListViewHasItem("members", user2.getUsername());
  }

  @Test
  public void testCorrectGroupId() {
    uiAssertions.assertLabel("groupId", "GroupID: " + group1.getGroupId());
  }

  // ListView of members is not clickable
  @Test
  public void testAdminVisibility() {
    Collection<User> admins = group1.getAdmins();
    assertNotEquals(0, admins.size(), "Every group should have admin. This group has no ones");
    // checking that all of the members are shown in the listView
    admins.forEach(admin -> uiAssertions.assertListViewHasItem("members", admin.getUsername()));
    // all of the admins shall be on the top of the listview
    for (int i = 0; i < admins.size(); i++) {
      uiAssertions.assertBoldText(uiAssertions.findListCell(i));
    }
  }

  @Test
  public void testBackButton(){
    clickOn("#backToHome");
    uiAssertions.assertOnScene(USER_HOME);
  }

  @Test
  public void testViewShiftButton(){
    clickOn("#goToShifts");
    uiAssertions.assertOnScene(SHIFT_VIEW);
  }
}
