package jobblett.ui;

import java.time.LocalDateTime;
import javafx.scene.control.ListCell;

public class JobShiftListCell extends ListCell<Integer> {

  private ControllerMap controllerMap;

  public JobShiftListCell(ControllerMap controllerMap) {
    this.controllerMap = controllerMap;
  }

  @Override public void updateItem(Integer jobShiftIndex, boolean empty) {
    setGraphic(null);
    if (empty || jobShiftIndex == null) {
      setText(null);
      return;
    }
    WorkspaceAccess access = controllerMap.getAccess();
    int activeGroupId = controllerMap.getActiveGroupId();

    /*TODO: bør gjøres på en bedre måte...
      Nå legger den ny listener hver gang den oppdateres (den gamle slettes ikke)*/
    boolean isNewItem = getItem() != jobShiftIndex;
    super.updateItem(jobShiftIndex, empty);

    final String shiftText = formatJobShift(jobShiftIndex);
    if (isNewItem) {
      setText(shiftText);
      selectedProperty().addListener((o, old, newValue) -> {
        if (isSelected()) {
          final String infoText = "\nInfo:\n" + access.getJobShiftInfo(activeGroupId, jobShiftIndex);
          setText(shiftText + infoText);
        } else {
          setText(shiftText);
        }
      });
    }

  }

  //String representation of the job shift, which is used by the cell
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
