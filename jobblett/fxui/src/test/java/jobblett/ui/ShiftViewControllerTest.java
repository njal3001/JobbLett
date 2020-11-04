package jobblett.ui;

import static jobblett.ui.JobblettScenes.SHIFT_VIEW_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


import org.junit.jupiter.api.Test;

import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import jobblett.core.Group;
import jobblett.core.JobShift;
import jobblett.core.User;

public class ShiftViewControllerTest extends JobbLettTest{

  @Override
  protected JobblettScenes giveID() {
    return SHIFT_VIEW_ID;
  }

  @Override
  protected User giveActiveUser(){
    return user1;
  }

  @Override
  protected Group giveActiveGroup(){
    return group1;
  }
  
  //vurder å lage en for å sjekke om info dukker opp når man klikker på dem, og ikk før det
  @Test
  public void testJobShiftsView_correctText() {
    ListView<JobShift> shifts = lookup("#shifts").query();
    clickOn(shifts);type(KeyCode.ENTER);
    //The shift on the top i jobshift2
    type(KeyCode.ENTER);
    String user=jobShift2.getUser().toString();
    String startingDate= jobShift2.getStartingTime().format(App.EXPECTED_DATE_FORMAT);
    String startingTime=jobShift2.getStartingTime().format(App.EXPECTED_TIME_FORMAT);
    String endingTime=jobShift2.getEndingTime().format(App.EXPECTED_TIME_FORMAT);
    String infoText= jobShift2.getInfo();
    
    String expectedSting = user + "\t" + startingDate + "\t" + startingTime + " - " + endingTime + "\nInfo:\n" + infoText;
    uiAssertions.assertListCellText(shifts.getSelectionModel().getSelectedIndex(), expectedSting);
  }

  @Test
  public void testJobShiftsView_isSorted() {
    ListView<JobShift> shifts = lookup("#shifts").query();
    clickOn(shifts);
    type(KeyCode.ENTER);
    JobShift shiftAtTop = shifts.getSelectionModel().getSelectedItem();
    type(KeyCode.DOWN);
    JobShift shiftAtBottom = shifts.getSelectionModel().getSelectedItem();
    assertTrue(shiftAtTop.getStartingTime().isBefore(shiftAtBottom.getStartingTime()), "The shifts are not correctly sorted in the ListView. They shall be sorted after startingTime.");

    //adding a shift manually that starts after the shift on top, but before the shift in the bottom
    clickOn("#newShiftButton");
    clickOn("#members");
    //choosing the first member
    type(KeyCode.ENTER);
    //Setting the same date as the two other shifts
    DatePicker date = lookup("#date").query();
    date.setValue(shiftAtTop.getStartingTime().toLocalDate());
    // Setting up shift with starting time right after the shift at the top
    clickOn("#fromField");
    type(KeyCode.BACK_SPACE,5);
    type(KeyCode.DELETE,5);
    int startHour= shiftAtTop.getStartingTime().getHour()+1;
    int startMinute= shiftAtTop.getStartingTime().getMinute();
    write(Integer.toString(startHour)+":"+Integer.toString(startMinute));
    clickOn("#toField");
    type(KeyCode.BACK_SPACE,5);
    type(KeyCode.DELETE,5);
    write(Integer.toString(startHour+1)+":"+Integer.toString(startMinute+1));
    // Creating the shift
    clickOn("#createShiftButton");

    //Now our new shift should be in the middle of the ListView.
    clickOn(shifts);
    assertEquals(3, shifts.getItems().size(), "There are not three shifts in the shiftView");
    type(KeyCode.ENTER);
    type(KeyCode.DOWN);
    JobShift newShift = shifts.getSelectionModel().getSelectedItem();
    assertNotEquals(shiftAtBottom, newShift, "The shift that shall be an the bottom, is in the middle.");
    assertNotEquals(shiftAtTop, newShift, "The shift that shall be on the top, is in the middle.");
    

  }

  @Test
  public void testDeleteShift(){
    ListView<JobShift> preShifts = lookup("#shifts").query();
    //checking the amount of shifts before deleting
    int amountBefore = preShifts.getItems().size();
    clickOn(preShifts);
    type(KeyCode.ENTER);
    JobShift selectedJobShift= preShifts.getSelectionModel().getSelectedItem();
    clickOn("#deleteShiftButton");
    //Checking if a shift got deleted, by controlling the amount after deleting a shift
    assertEquals(amountBefore-1, preShifts.getItems().size(),"A shift didn't get deleted");
    //Checking if the shift that got deleted is the shift we wanted to delete
    uiAssertions.assertListViewHasNotItem("shifts",selectedJobShift);
  }
}