package jobblett.ui;

import org.junit.jupiter.api.Test;

import jobblett.core.Group;
import jobblett.core.User;

public class GroupHomeTest extends JobbLettTest{

  @Override
  protected String giveID() {
    return App.GROUP_HOME_ID;
  }

  @Override
  protected User giveActiveUser(){
    return user1;
  }

  @Override
  protected Group giveActiveGroup(){
    return group1;
  }
  
  @Test
  public void testMembersShowingInView(){
    uiAssertions.assertListViewHasItem("members", user1);
    uiAssertions.assertListViewHasItem("members", user2);
  }

  @Test
  public void testCorrectGroupId(){
    uiAssertions.assertText("groupID", "GroupID: " + group1.getGroupID());
  }
}