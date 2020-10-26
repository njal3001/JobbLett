package jobblett.ui;

import org.junit.jupiter.api.Test;

import jobblett.core.Group;
import jobblett.core.User;

public class UserHomeControllerTest extends JobbLettTest {

  @Override
  protected String giveID() {
    return App.USER_HOME_ID;
  }

  @Override
  protected User giveActiveUser() {
    return user1;
  }

  @Override
  protected Group giveActiveGroup() {
    return null;
  }

  @Test
  public void testGroupsView_initialGroup(){
    uiAssertions.assertListViewHasItem("groups", group1);
  }

  @Test
  public void testGoTo_GroupHome() {
    clickOn(uiAssertions.findListCell(0));
    uiAssertions.assertOnScene(App.GROUP_HOME_ID);
  }

  @Test
  public void testGoTo_JoinGroup() {
    clickOn("#joinGroupButton");
    uiAssertions.assertOnScene(App.JOIN_GROUP_ID);
  }

  @Test
  public void testGoTo_CreateGroup() {
    clickOn("#createGroupButton");
    uiAssertions.assertOnScene(App.CREATE_GROUP_ID);
  }
}