package jobblett.ui;

import java.time.LocalDateTime;
import javafx.scene.control.ListCell;

/**
 * CellFactory for the listView showing the shifts ShiftViewController.
 */
public class JobShiftListCell extends ListCell<Integer> {

  private ControllerMap controllerMap;

  public JobShiftListCell(ControllerMap controllerMap) {
    this.controllerMap = controllerMap;
  }

  @Override public void updateItem(Integer jobShiftIndex, boolean empty) {
    super.updateItem(jobShiftIndex, empty);
    setGraphic(null);
    if (empty || jobShiftIndex == null) {
      setText(null);
      return;
    }
    WorkspaceAccess access = controllerMap.getAccess();
    int activeGroupId = controllerMap.getActiveGroupId();

    final String shiftText = formatJobShift(jobShiftIndex);
    final String infoText = "\nInfo:\n" + access.getJobShiftInfo(activeGroupId, jobShiftIndex);
    setText(shiftText + infoText);
  }

  /**
   * String representation of the jobShift, which is used by the cell.
   *
   * @param jobShiftIndex the jobShift that we want string representation of.
   * @return the string representation
   */
  private String formatJobShift(int jobShiftIndex) {
    WorkspaceAccess access = controllerMap.getAccess();
    int activeGroupId = controllerMap.getActiveGroupId();

    String s = "";
    String username = access.getJobShiftUsername(activeGroupId, jobShiftIndex);
    s += access.getUserToString(username) + "\t";
    LocalDateTime startingDateTime = access.getJobShiftStartingTime(activeGroupId, jobShiftIndex);
    s += startingDateTime.format(App.EXPECTED_DATE_FORMAT) + "\t";
    LocalDateTime endingDateTime = access.getJobShiftEndingTime(activeGroupId, jobShiftIndex);
    s += startingDateTime.format(App.EXPECTED_TIME_FORMAT);
    s += " - ";
    s += endingDateTime.format(App.EXPECTED_TIME_FORMAT);
    return s;
  }
}
