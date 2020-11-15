package jobblett.ui;

import java.time.LocalDateTime;
import javafx.scene.control.ListCell;
import jobblett.core.JobShift;

public class JobShiftListCell extends ListCell<JobShift> {

  //Basic implementasjon av celle for job shift view i ShiftViewController, burde sikkert 
  //endre de to formaterings metodene i bunnen av klassen, men de fungerer

  @Override public void updateItem(JobShift jobShift, boolean empty) {
    setGraphic(null);
    if (empty || jobShift == null) {
      setText(null);
      return;
    }

    /*TODO: bør gjøres på en bedre måte...
       Nå legger den ny listener hver gang den oppdateres (den gamle slettes ikke)*/
    boolean isNewItem = getItem() != jobShift;
    super.updateItem(jobShift, empty);

    final String shiftText = formatJobShift(jobShift);
    if (isNewItem) {
      setText(shiftText);
      selectedProperty().addListener((o, old, newValue) -> {
        if (isSelected()) {
          final String infoText = "\nInfo:\n" + jobShift.getInfo();
          setText(shiftText + infoText);
        } else {
          setText(shiftText);
        }
      });
    }

  }


  //String representation of the job shift, which is used by the cell
  private String formatJobShift(JobShift jobShift) {
    String s = "";
    if (jobShift.getUser() != null) {
      s += jobShift.getUser().toString() + "\t";
    }
    LocalDateTime startingDateTime = jobShift.getStartingTime();
    s += startingDateTime.format(App.EXPECTED_DATE_FORMAT) + "\t";
    LocalDateTime endingDateTime = jobShift.getEndingTime();
    s += startingDateTime.format(App.EXPECTED_TIME_FORMAT);
    s += " - ";
    s += endingDateTime.format(App.EXPECTED_TIME_FORMAT);
    return s;
  }
}
