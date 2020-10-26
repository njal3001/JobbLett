package jobblett.ui;

import javafx.application.Platform;
import javafx.scene.control.ListCell;
import org.assertj.core.internal.bytebuddy.asm.Advice;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

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
  // Creating dates for the tests
LocalDateTime tomorrow = LocalDateTime.now().plusDays(1);
LocalDateTime yesterday = LocalDateTime.now().minusDays(1);

DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

// tomorrow in string
String dateInFuture = tomorrow.format(formatter);
//yesterday in string
String dateInPast = yesterday.format(formatter);


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
    assertNotEquals("", ErrorMessageField.getText(), "The user should get an errormessage when creating an invalid shift");
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
    //choosing the first memeber
    type(KeyCode.ENTER);
    clickOn("#date");
    //erasing the prewritten date
    type(KeyCode.BACK_SPACE,10);
    //writing the date for tomorrow(future)
    write(dateInFuture);
    type(KeyCode.ENTER);
    // Setting up valid times for the shifts
    clickOn("#fromField");
    write("08:00");
    uiAssertions.assertTextField("fromField", "08:00");
    clickOn("#toField");
    write("15:30");
    uiAssertions.assertTextField("toField", "15:30");
    // Adding additional info for the shift
    clickOn("#infoArea");
    write("We are testing valid createShift");
    uiAssertions.assertTextArea("infoArea", "We are testing valid createShift");
    // Creating the shift
    clickOn("#createShiftButton");

    // verifying that the shift is showing in the listview
    assertEquals((amountBefore + 1), preShifts.getItems().size(),
        "The recently created shift wasn't added in the ShiftView");
   }

  // Testing that you´re not able to create different invalid shifts:
  @Test
  public void testShiftInPast() {
    clickOn("#members");
    type(KeyCode.ENTER);
    // The date will be set to yesterday to make sure that the shift is in the past.
    clickOn("#date");
    //erasing the prewritten date
    type(KeyCode.BACK_SPACE,10);
    //writing the date for yesterday(past)
    write(dateInPast);
    type(KeyCode.ENTER);
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
    //verifying that we are getting correct errormessage
    uiAssertions.assertText("errorMessage", "Starting time must be later than the current time");
  }

  @Test
  public void testInvalidTimeFormat(){
    clickOn("#members");
    type(KeyCode.ENTER);
    // The date will be set to yesterday to make sure that the shift is in the past.
    clickOn("#date");
    //erasing the prewritten date
    type(KeyCode.BACK_SPACE,10);
    //writing the date for yesterday(past)
    write(dateInFuture);
    type(KeyCode.ENTER);
    // Setting up ivalid times for the shifts
    clickOn("#fromField");
    write("wrongInput");
    clickOn("#toField");
    write("123Wrong");
    // Adding additional info for the shift
    clickOn("#infoArea");
    write("We are testing creating shift with invalid timeformat");
    // Creating the shift
    clickOn("#createShiftButton");

    // The shift should not have been created, and we should still be in UpdateShift.fxml with an error message.
    // Verifying that we are getting an errormessage in UpdateShift.fxml
    assertNotEquals("", ((Text) lookup("#errorMessage").query()).getText(), "No errormessages are shown");
    //verifying that we are getting correct errormessage
    uiAssertions.assertText("errorMessage", "Time period is not written in the correct format");

  }

  @Test
  public void updateShift(){
    UpdateShiftController shiftController = (UpdateShiftController) mainController.getSceneController(App.UPDATE_SHIFT_ID);
    shiftController.setActiveJobShift(jobShift1);
    Platform.runLater(() -> {shiftController.onSceneDisplayed();});
    // shiftController.onSceneDisplayed();
    clickOn("#date");
    type(KeyCode.RIGHT,1);
    clickOn("#members");
    type(KeyCode.ENTER);
    uiAssertions.assertDate("date", "2020-10-26");
    // The date will be set to yesterday to make sure that the shift is in the past.
    clickOn("#date");
    //erasing the prewritten date
    type(KeyCode.BACK_SPACE,10);
    //writing the date for yesterday(past)
    write(dateInFuture);
    type(KeyCode.ENTER);
    // Setting up ivalid times for the shifts
    clickOn("#fromField");
    type(KeyCode.BACK_SPACE,10);
    type(KeyCode.DELETE,10);
    write("10:00");
    uiAssertions.assertTextField("fromField", "10:00");
    clickOn("#toField");
    type(KeyCode.BACK_SPACE,10);
    type(KeyCode.DELETE,10);
    write("17:00");
    uiAssertions.assertTextField("toField","17:00");
    clickOn("#infoArea");
    type(KeyCode.BACK_SPACE,20);
    type(KeyCode.DELETE,20);
    write("We are testing if update jobshift is working");
    uiAssertions.assertTextArea("infoArea","We are testing if update jobshift is working");
    // Creating the shift
    clickOn("#createShiftButton");
  }

  @Test
  public void testUpdatedShiftViewCell(){
    ListCell jobShiftListCell = uiAssertions.findListCell(0);
    jobShiftListCell.equals(jobShift1);

  }

  public static void main(String[] args) {

  }

}
