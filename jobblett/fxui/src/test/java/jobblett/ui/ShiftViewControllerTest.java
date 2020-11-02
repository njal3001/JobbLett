package jobblett.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import jobblett.core.Group;
import jobblett.core.JobShift;
import jobblett.core.User;

public class ShiftViewControllerTest extends JobbLettTest{

  @Override
  protected String giveID() {
    return App.SHIFT_VIEW_ID;
  }

  @Override
  protected User giveActiveUser(){
    return user1;
  }

  @Override
  protected Group giveActiveGroup(){
    return group1;
  }
  
  //TODO: To be implemented
  @Test
  public void testJobShiftsView_correctText() {
  }

  @Test
  public void testJobShiftsView_isSorted() {
  }

  @Test
  public void testDeleteShift(){

    // TODO: Control that you are deleting the shift you selected
    //checking the amount of shifts before deleting
    ListView<JobShift> preShifts = lookup("#shifts").query();
    int amountBefore = preShifts.getItems().size();
    clickOn("#shifts");
    type(KeyCode.ENTER);
    clickOn("#deleteShiftButton");
    //Checking if the shift got deleted, by controlling the amount after deleting a shift
    assertEquals(amountBefore-1, preShifts.getItems().size(),"A shift didn't get deleted");
    
  }
}