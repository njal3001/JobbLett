package jobblett.ui;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import jobblett.core.User;

//Koder som er kommentert ut skal implementeres ettersom deres funksjoner er implementert i controlleren.
public class CreateShiftControllerTest extends JobbLettTest {

  @Override
  protected String giveFxmlFileName() {
    return "CreateShift.fxml";
  }

  @Override
  protected void setupData() {
    super.setupData();
    main.logIn(user1);
    main.setActiveGroup(group1);
  }

    @Test
  public void testGoBack(){
    clickOn("#goBackButton");
    //checks if a node in the ShiftView.fxml exists on the current screen, which confirms that we are on the correct scene
    Button nodeInShiftViewScene = ((Button) lookup("#newShiftButton").query());
    assertNotNull(nodeInShiftViewScene);
  }
  /*
  @Test
  public void testGetStartingTime(){

  }*/

  /*@Test
  public void testCreateValidShift(){
    ListView<User> members= lookup("#members").query();
    assertNotNull(members);
    clickOn(members.)
  }*/


  @Test
  public void testNotEmptyUserList(){
    ListView<User> members= lookup("#members").query();
    assertNotNull(members);
    //There should be more than zero members in the ListView
    assertNotEquals(0,members.getItems().size());
  }
//må ha mer errormessage i createshift!
}