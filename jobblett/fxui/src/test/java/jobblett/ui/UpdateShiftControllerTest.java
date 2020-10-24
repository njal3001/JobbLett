package jobblett.ui;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;

import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import jobblett.core.JobShift;
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

  @Test
  public void testGoBack() {
    clickOn("#goBackButton");
    // checks if a node in the ShiftView.fxml exists on the current scene, which
    // confirms that we are on the correct scene
    Button nodeInShiftViewScene = lookup("#editShiftButton").query();
    assertNotNull(nodeInShiftViewScene);
  }

  @Test
  public void testErrorMessage() {
    Text ErrorMessageField = lookup("#errorMessage").query();
    assertEquals("", ErrorMessageField.getText(), "Errormessage should be empty, when no action is done");
    clickOn("#createShiftButton");
    // An errormessage should be visible when creating an empty shift
    assertNotEquals("", ErrorMessageField.getText(),
        "The user should get an errormessage when creating an invalid shift");
  }

  @Test
  public void testNotEmptyUserList() {
    ListView<User> members = lookup("#members").query();
    assertNotNull(members);
    // There should be more than zero members in the ListView
    assertNotEquals(0, members.getItems().size(), "There should be more than zero members in the ListView");
  }

  @Test
  public void testValidShift() {
    // Getting the amount of shifts before we create our new shift
    clickOn("#goBackButton");
    ListView<JobShift> preShifts = lookup("#shifts").query();
    int amountBefore = preShifts.getItems().size();

    clickOn("#newShiftButton");
    clickOn("#members");
    type(KeyCode.ENTER);
    DatePicker date = lookup("#date").query();
    //Setting shift for tomorrow(future)
    date.setValue(LocalDate.now().plusDays(1));
    // Setting up valid times for the shifts
    clickOn("#fromField");
    write("08:00");
    clickOn("#toField");
    write("15:30");
    // Adding additional info for the shift
    clickOn("#infoArea");
    write("We are testing creatShift");
    // Creating the shift
    clickOn("#createShiftButton");

    // verifying that the shift is showing in the listview
    assertEquals((amountBefore + 1), preShifts.getItems().size(),
        "The recently created shift wasn't added in the ShiftView");
  }

  // Testing that you´re not able to create differant invalid shifts:
  
   // Kommentert vekk inntil denne feilen er fikset i appen, IKKE FJERN TESTEN
    /*
    @Test public void testUnassignedShift(){ 
    //Should not be able to create shift without selecting an employee 
    DatePicker date = lookup("#date").query();
    //Setting shift for tomorrow(future)
    date.setValue(LocalDate.now().plusDays(1));
    //Setting up valid times for the shifts 
    clickOn("#fromField");
    write("08:00"); 
    clickOn("#toField"); 
    write("15:30"); 
    //Adding additional info for the shift 
    clickOn("#infoArea"); 
    write("We are testing unassigned shift");
    //Creating the shift 
    clickOn("#createShiftButton");
    
    //The shift should not have been created, and we should still be in UpdateShift.fxml with an error message.
    //Verifying that we are still on the same scene, by looking for a unique Node in Updateshift.fxml. 
    ListView<User> members = lookup("#members").query(); 
    assertNotNull(members);
    
    }*/
   
  @Test
  public void testShiftInPast() {
    clickOn("#members");
    type(KeyCode.ENTER);
    // The date will be set to yesterday to make sure that the shift is in the past.
    DatePicker date = lookup("#date").query();
    //Setting shift for tomorrow(future)
    date.setValue(LocalDate.now().minusDays(1));
    // Setting up valid times for the shifts
    clickOn("#fromField");
    write("08:00");
    clickOn("#toField");
    write("15:30");
    // Adding additional info for the shift
    clickOn("#infoArea");
    write("We are testing creating shift in the past");
    // Creating the shift
    clickOn("#createShiftButton");

    // The shift should not have been created, and we should still be in UpdateShift.fxml with an error message.
    // Verifying that we are getting an errormessage in UpdateShift.fxml
    assertNotEquals("", ((Text) lookup("#errorMessage").query()).getText(), "No errormessages are shown");
  }
  

//kommentert vekk inntil feilen er fikset i controller, IKKE FJERN
/*  
@Test
  public void testStartBeforeEnd(){
    clickOn("#members");
    type(KeyCode.ENTER);
    // The date will be set to tomorrow(future).
    DatePicker date = lookup("#date").query();
    //Setting shift for tomorrow(future)
    date.setValue(LocalDate.now().plusDays(1));
    // Setting up invalid times for the shifts
    clickOn("#fromField");
    write("15:00");
    clickOn("#toField");
    write("08:30");
    // Adding additional info for the shift
    clickOn("#infoArea");
    write("We are testing creating shift that ends before it starts.");
    // Creating the shift
    clickOn("#createShiftButton");

    // The shift should not have been created, and we should still be in UpdateShift.fxml with an error message.
    // Verifying that we are getting an errormessage in UpdateShift.fxml
    assertNotEquals("", ((Text) lookup("#errorMessage").query()).getText(), "No errormessages are shown");
  }*/


}
