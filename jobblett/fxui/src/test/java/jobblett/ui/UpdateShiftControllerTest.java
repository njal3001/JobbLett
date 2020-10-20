package jobblett.ui;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import javafx.scene.control.ListView;
import jobblett.core.User;


//Koder som er kommentert ut skal implementeres ettersom deres funksjoner er implementert i controlleren.
public class UpdateShiftControllerTest extends JobbLettTest {

  @Override
  protected String giveID() {
    return App.UPDATE_SHIFT_ID;
  }

  @Override
  protected void setupData() {
    super.setupData();
    activeUser = user1;
    activeGroup = group1;
  }
  

  /*
  @Test
  public void testGoBack(){
    clickOn("#goBackButton");
    //checks if a node in the ShiftView.fxml exists on the current screen, which confirms that we are on the correct scene
    Button nodeInShiftViewScene = lookup("#newShiftButton").query();
    assertNotNull(nodeInShiftViewScene);
  }

  */
  /*
  @Test
  public void testGetStartingTime(){
  }
  
  //må ha mer errormessage i createshift og endre litt på createshift før vi lager denne delen.
  @Test
  public void testCreateValidShift(){
  }
    ListView<User> members= lookup("#members").query();
    assertNotNull(members);
    clickOn(members.)
  }*/


  @Test
  public void testNotEmptyUserList(){
    ListView<User> members = lookup("#members").query();
    assertNotNull(members);
    //There should be more than zero members in the ListView
    assertNotEquals(0,members.getItems().size());
  }
  //må ha mer errormessage i createshift!
}
