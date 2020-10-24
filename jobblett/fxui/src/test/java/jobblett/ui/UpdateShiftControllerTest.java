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
import jobblett.core.Group;
import jobblett.core.JobShift;
import jobblett.core.User;

//Koder som er kommentert ut skal implementeres ettersom deres funksjoner er implementert i controlleren.
public class UpdateShiftControllerTest extends JobbLettTest {

  @Override
  protected String giveID() {
    return App.UPDATE_SHIFT_ID;
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
  public void testGoBack() {
    clickOn("#goBackButton");
    uiAssertions.assertOnScene(App.SHIFT_VIEW_ID);
  }

  // Denne metoden burde gjøres litt mer dekkende, altså at riktige feilmeldinger kommer opp i forhold til
  // hva som var feil med inputen
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
  public void testUsersView_HasUsers() {
    uiAssertions.assertListViewHasItem("members", user1);
    uiAssertions.assertListViewHasItem("members", user2);
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
