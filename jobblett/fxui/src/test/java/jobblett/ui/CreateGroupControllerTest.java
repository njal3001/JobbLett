package jobblett.ui;

import org.junit.jupiter.api.Test;

import jobblett.core.Group;
import jobblett.core.User;

public class CreateGroupControllerTest extends JobbLettTest {

  @Override
  protected String giveID() { 
    return App.CREATE_GROUP_ID;
  }

  @Override
  protected User giveActiveUser(){
    return user1;
  }

  @Override
  protected Group giveActiveGroup(){
    return null;
  }

  //Forstår ikke hvorfor den ikke bare kan bruke groupList og må bruke mainController.getGroupList() istedet.
  //Burde kanskje endre noe siden nå vil oppdateres ikke testdataen underveis i testene, må bruke maincontroller
  //for å få tak i den nyeste versjonene av dataene 
  @Test
  public void testCreateGroup_validGroupName() {
    tryToCreateGroup("Group Name");
    uiAssertions.assertOnScene(App.GROUP_HOME_ID);
    uiAssertions.assertText("groupName", "Group Name");
    int groupID = mainController.getActiveGroup().getGroupID();
    clickOn("#backToHome");
    uiAssertions.assertOnScene(App.USER_HOME_ID);
    uiAssertions.assertListViewHasItem("groups", mainController.access.getGroup(groupID));
  }

  @Test
  public void testCreateGroup_invalidGroupName() {
    tryToCreateGroup("    ");
    uiAssertions.assertOnScene(App.CREATE_GROUP_ID);
    uiAssertions.assertText("errorMessage", "Group name must have at least 2 characters");
  }

  private void tryToCreateGroup(String groupName) {
    clickOn("#groupNameField").write(groupName);
    clickOn("#createGroupButton");
  }
}