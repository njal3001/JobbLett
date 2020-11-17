package jobblett.ui;

import static jobblett.ui.JobblettScenes.SHIFT_VIEW;
import static jobblett.ui.JobblettScenes.GROUP_HOME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.scene.control.CheckBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import jobblett.core.Group;
import jobblett.core.JobShift;
import jobblett.core.User;
import java.time.Duration;
import java.time.LocalDateTime;

public class ShiftViewControllerTest extends JobbLettTest {

  @Override
  protected JobblettScenes giveId() {
    return SHIFT_VIEW;
  }

  @Override
  protected User optionalActiveUser() {
    return user1;
  }

  @Override
  protected Group optionalActiveGroup() {
    return group1;
  }

  @Test
  public void testJobShiftView_notShowingPastShifts(){
    JobShift jobShiftInFuture = new JobShift(user1, LocalDateTime.now().plusHours(5), Duration.ofHours(5), "Shift in future");
    JobShift jobShiftInPast = new JobShift(user1, LocalDateTime.now().minusHours(6), Duration.ofHours(5), "Shift in past");
    group1.addJobShift(jobShiftInFuture, user1);
    group1.addJobShift(jobShiftInPast, user1);
    //let the listView update by going back and the return
    clickOn("#backToGroup");
    clickOn("#goToShifts");
    uiAssertions.assertHasJobShift(jobShiftInFuture);
    uiAssertions.assertHasNotJobShift(jobShiftInPast);
    //TODO: test for sjekke over natta
  }

  @Test
  public void testJobShiftsView_isSorted() throws InterruptedException {
    uiAssertions.assertJobShiftListCellText(jobShift2, 0);
    uiAssertions.assertJobShiftListCellText(jobShift1, 1);
    uiAssertions.assertJobShiftListCellText(jobShift3, 2);
  }

  @Test
  public void testUserFilter() {
    uiAssertions.assertListViewSize("shifts", 3);
    CheckBox toggleUserFilterCheckBox = (CheckBox) lookup("#toggleUserFilterCheckBox").query();
    clickOn(toggleUserFilterCheckBox);
    uiAssertions.assertListViewSize("shifts", 2);
    uiAssertions.assertJobShiftListCellText(jobShift2, 0);
    uiAssertions.assertJobShiftListCellText(jobShift1, 1);
    clickOn(toggleUserFilterCheckBox);
    uiAssertions.assertListViewSize("shifts", 3);
  }

  @Test
  public void testDeleteShift() {
    clickOn(uiAssertions.findListCell(0));
    clickOn("#deleteShiftButton");
    uiAssertions.assertListViewSize("shifts", 2);
    uiAssertions.assertHasNotJobShift(jobShift2);
  }

  @Test
  public void testGoBackButton(){
    clickOn("#backToGroup");
    uiAssertions.assertOnScene(GROUP_HOME);
  }
}
