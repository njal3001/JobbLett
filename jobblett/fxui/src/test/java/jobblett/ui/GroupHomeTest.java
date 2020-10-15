package jobblett.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

import javafx.scene.control.ListView;
import javafx.scene.text.Text;

public class GroupHomeTest extends JobbLettTest{

  @Override
  protected String giveID() {
    return App.GROUP_HOME_ID;
  }
  
  @Override
  protected void setupData(){
    super.setupData();
    main.logIn(user1);
    main.setActiveGroup(group1);
  }

  @Test
  public void testMembersShowingInView(){
    assertNotNull(user1);
    assertListViewHasItem(user1.toString());
    assertListViewHasItem(user2.toString());
  }

  @Test
  public void testCorrectGroupId(){
    Text groupIdText = lookup("#groupID").query();
    assertEquals("GroupID: " + String.valueOf(group1.getGroupID()), groupIdText.getText());
  }

  private void assertListViewHasItem(String itemText){
    ListView<Text> members = lookup("#members").query();
    assertNotNull(members.getItems().stream().filter(text -> text.getText().equals(itemText)).findFirst().orElse(null));
  }
}