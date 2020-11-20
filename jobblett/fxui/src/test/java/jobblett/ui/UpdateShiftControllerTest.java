package jobblett.ui;

import static jobblett.ui.JobblettScenes.SHIFT_VIEW;
import static jobblett.ui.JobblettScenes.UPDATE_SHIFT;
import java.time.LocalDate;

import javafx.scene.control.DatePicker;
import javafx.scene.input.KeyCode;
import jobblett.core.Group;
import jobblett.core.User;
import org.junit.jupiter.api.Test;

public class UpdateShiftControllerTest extends JobbLettTest {

  @Override public JobblettScenes giveId() {
    return UPDATE_SHIFT;
  }

  @Override public User optionalActiveUser() {
    return user1;
  }

  @Override public Group optionalActiveGroup() {
    return group1;
  }

  @Test public void testGoBack() {
    clickOn("#goBackButton");
    uiAssertions.assertOnScene(SHIFT_VIEW);
  }

  @Test 
  public void testUsersView_HasUsers() {
    uiAssertions.assertListViewHasItem("members", user1.getUsername());
    uiAssertions.assertListViewHasItem("members", user2.getUsername());
  }

  @Test 
  public void testCreateShift_valid() {
    tryToCreateJobShift(0, newShift.getStartingTime().toLocalDate(),
         "15:00", "17:00", newShift.getInfo());
    uiAssertions.assertOnScene(JobblettScenes.SHIFT_VIEW);
    uiAssertions.assertListViewSize("shifts", 4);
    uiAssertions.assertHasJobShift(newShift);
  }

  @Test 
  public void testCreateJobShift_inPast() {
    tryToCreateJobShift(0, LocalDate.now().minusDays(1), 
        "10:00", "15:00", "Test");
    uiAssertions.assertOnScene(JobblettScenes.UPDATE_SHIFT);
    uiAssertions.assertLabel("errorMessage", "Starting time must be later than the current time");
  }

  @Test 
  public void testCreateJobShift_invalidTimeFormat() {
    tryToCreateJobShift(0, LocalDate.now(), "15", "37", "Test");
    uiAssertions.assertOnScene(JobblettScenes.UPDATE_SHIFT);
    uiAssertions.assertLabel("errorMessage", "Time period is not written in the correct format");
  } 

  @Test public void testUpdateShift() {
    clickOn("#goBackButton");
    clickOn(uiAssertions.findListCell(0));
    clickOn("#editShiftButton");
    tryToCreateJobShift(0, newShift.getStartingTime().toLocalDate(),
         "15:00", "17:00", newShift.getInfo());
    
    uiAssertions.assertOnScene(JobblettScenes.SHIFT_VIEW);
    uiAssertions.assertHasJobShift(newShift);
    uiAssertions.assertHasNotJobShift(jobShift2);
  }

  private void tryToCreateJobShift(int userIndex, LocalDate date, 
      String startingTime, String endingTime, String info) {
    clickOn(uiAssertions.findListCell(userIndex));
    ((DatePicker) lookup("#date").query()).setValue(date);
    clickOn("#fromField").type(KeyCode.BACK_SPACE, 5)
        .type(KeyCode.DELETE, 5).write(startingTime);
    clickOn("#toField").type(KeyCode.BACK_SPACE, 5)
        .type(KeyCode.DELETE, 5).write(endingTime);
    clickOn("#infoArea").type(KeyCode.BACK_SPACE, 20)
        .type(KeyCode.DELETE, 20).write(info);
    clickOn("#createShiftButton");
  }
}
