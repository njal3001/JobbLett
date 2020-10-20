package jobblett.ui;

import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GroupHomeTest extends JobbLettTest{

  @Override
  protected String giveID() {
    return App.GROUP_HOME_ID;
  }
  
  @Override
  protected void setupData(){
    super.setupData();
    activeUser = user1;
    activeGroup = group1;
  }

  @Test
  public void testMembersShowingInView(){
    assertListViewHasItem(user1.toString());
    assertListViewHasItem(user2.toString());
  }

  @Test
  public void testCorrectGroupId(){
    Text groupIdText = lookup("#groupID").query();
    assertEquals("GroupID: " + group1.getGroupID(), groupIdText.getText());
  }

  private void assertListViewHasItem(String itemText){
    ListView<Text> members = lookup("#members").query();
    assertNotNull(members.getItems().stream().filter(text -> text.getText().equals(itemText)).findFirst().orElse(null));
  }
}