package jobblett.ui;

import static jobblett.ui.JobblettScenes.SHIFT_VIEW;
import static jobblett.ui.JobblettScenes.UPDATE_SHIFT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javafx.application.Platform;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import jobblett.core.Group;
import jobblett.core.JobShift;
import jobblett.core.User;
import org.junit.jupiter.api.Test;

//Koder som er kommentert ut skal implementeres ettersom deres funksjoner er implementert i controlleren.
public class UpdateShiftControllerTest extends JobbLettTest {
  // Creating dates for the tests
  LocalDate tomorrow = LocalDate.now().plusDays(1);
  LocalDate yesterday = LocalDate.now().minusDays(1);

  ////disse brukes ikke//////
  // tomorrow in string
  String dateInFuture = tomorrow.format(App.EXPECTED_DATE_FORMAT);
  //yesterday in string
  String dateInPast = yesterday.format(App.EXPECTED_DATE_FORMAT);


  @Override protected JobblettScenes giveId() {
    return UPDATE_SHIFT;
  }

  @Override protected User optionalActiveUser() {
    return user1;
  }

  @Override protected Group optionalActiveGroup() {
    return group1;
  }

  @Test public void testGoBack() {
    clickOn("#goBackButton");
    uiAssertions.assertOnScene(SHIFT_VIEW);
  }

/*
  // Denne metoden burde gjøres litt mer dekkende, altså at riktige feilmeldinger kommer opp i forhold til
  // hva som var feil med inputen
  @Test
  public void testErrorMessage() {
    Text ErrorMessageField = lookup("#errorMessage").query();
    assertEquals("", ErrorMessageField.getText(), "Errormessage should be empty, when no action is done");
    clickOn("#createShiftButton");
    // An errormessage should be visible when creating an empty shift
    assertNotEquals("", ErrorMessageField.getText(), "The user should get an errormessage when creating an invalid shift");
  }*/

  @Test public void testUsersView_HasUsers() {
    uiAssertions.assertListViewHasItem("members", user1);
    uiAssertions.assertListViewHasItem("members", user2);
  }

  @Test public void testValidShift() {
    // Getting the amount of shifts before we create our new shift
    clickOn("#goBackButton");
    ListView<JobShift> preShifts = lookup("#shifts").query();
    int amountBefore = preShifts.getItems().size();

    clickOn("#newShiftButton");
    clickOn("#members");
    //choosing the first member
    type(KeyCode.ENTER);
    //Setting the date for tomorrow(future)
    DatePicker date = lookup("#date").query();
    date.setValue(tomorrow);
    // Setting up valid times for the shifts
    clickOn("#fromField");
    type(KeyCode.BACK_SPACE, 5);
    type(KeyCode.DELETE, 5);
    write("08:00");
    uiAssertions.assertTextField("fromField", "08:00");
    clickOn("#toField");
    type(KeyCode.BACK_SPACE, 5);
    type(KeyCode.DELETE, 5);
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
  @Test public void testShiftInPast() {
    clickOn("#members");
    type(KeyCode.ENTER);
    // The date will be set to yesterday to make sure that the shift is in the past.
    DatePicker date = lookup("#date").query();
    date.setValue(yesterday);
    // Setting up valid times for the shifts
    clickOn("#fromField");
    type(KeyCode.BACK_SPACE, 5);
    type(KeyCode.DELETE, 5);
    write("08:00");
    clickOn("#toField");
    type(KeyCode.BACK_SPACE, 5);
    type(KeyCode.DELETE, 5);
    write("15:30");
    // Adding additional info for the shift
    clickOn("#infoArea");
    write("We are testing creating shift in the past");
    // Creating the shift
    clickOn("#createShiftButton");

    // The shift should not have been created, and we should still be in UpdateShift.fxml with an error message.
    // Verifying that we are getting an errormessage in UpdateShift.fxml
    assertNotEquals("", ((Label) lookup("#errorMessage").query()).getText(),
        "No errormessages are shown");
    //verifying that we are getting correct errormessage
    uiAssertions.assertLabel("errorMessage", "Starting time must be later than the current time");
  }

  @Test public void testInvalidTimeFormat() {
    clickOn("#members");
    type(KeyCode.ENTER);
    //Setting the date for tomorrow(future)
    DatePicker date = lookup("#date").query();
    date.setValue(tomorrow);
    // Setting up ivalid times for the shifts
    clickOn("#fromField");
    type(KeyCode.BACK_SPACE, 5);
    type(KeyCode.DELETE, 5);
    write("wrongInput");
    clickOn("#toField");
    type(KeyCode.BACK_SPACE, 5);
    type(KeyCode.DELETE, 5);
    write("123Wrong");
    // Adding additional info for the shift
    clickOn("#infoArea");
    write("We are testing creating shift with invalid timeformat");
    // Creating the shift
    clickOn("#createShiftButton");

    // The shift should not have been created, and we should still be in UpdateShift.fxml with an error message.
    // Verifying that we are getting an errormessage in UpdateShift.fxml
    assertNotEquals("", ((Label) lookup("#errorMessage").query()).getText(),
        "No errormessages are shown");
    //verifying that we are getting correct errormessage
    uiAssertions.assertLabel("errorMessage", "Time period is not written in the correct format");

  }

  @Test public void testUpdateShift() {
    UpdateShiftController shiftController = (UpdateShiftController) UPDATE_SHIFT.getController();
    shiftController.setActiveJobShift(jobShift1);
    Platform.runLater(() -> {
      shiftController.onSceneDisplayed();
    });
    //choosing the user on top
    clickOn("#members");
    type(KeyCode.ENTER);
    uiAssertions.assertDate("date", LocalDateTime.now().plusHours(5).format(App.EXPECTED_DATE_FORMAT));
    //Setting the date for tomorrow(future)
    DatePicker date = lookup("#date").query();
    date.setValue(tomorrow);
    // Setting up ivalid times for the shifts
    clickOn("#fromField");
    type(KeyCode.BACK_SPACE, 10);
    type(KeyCode.DELETE, 10);
    write("10:00");
    uiAssertions.assertTextField("fromField", "10:00");
    clickOn("#toField");
    type(KeyCode.BACK_SPACE, 10);
    type(KeyCode.DELETE, 10);
    write("17:00");
    uiAssertions.assertTextField("toField", "17:00");
    clickOn("#infoArea");
    type(KeyCode.BACK_SPACE, 20);
    type(KeyCode.DELETE, 20);
    write("We are testing if update jobshift is working");
    uiAssertions.assertTextArea("infoArea", "We are testing if update jobshift is working");
    // Creating the shift
    clickOn("#createShiftButton");

    //testing that the updates shift is still references as the same shift
    ListCell jobShiftListCell = uiAssertions.findListCell(0);
    jobShiftListCell.equals(jobShift1);
  }

  //TODO vi må fikse test som ikke lar det trykke på tidligere datoer
/*  @Test public void datePickerTest() {
    DatePicker datePicker = lookup("#date").query();
    datePicker.setValue(yesterday);
    System.out.println(datePicker.getValue());
  }*/

}
