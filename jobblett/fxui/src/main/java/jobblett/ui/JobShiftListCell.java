package jobblett.ui;

import java.time.LocalDateTime;

import javafx.event.EventHandler;
import javafx.scene.control.ListCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import jobblett.core.JobShift;

public class JobShiftListCell extends ListCell<JobShift>{

  //Basic implementasjon av celle for job shift view i ShiftViewController, burde sikkert 
  //endre de to formaterings metodene i bunnen av klassen, men de fungerer

  public JobShiftListCell() {
    setSkin(new JobblettCellSkin<>(this));
  }
  
  @Override
  public void updateItem(JobShift jobShift, boolean empty){
    super.updateItem(jobShift, empty);
    if(empty || jobShift == null) {
      setGraphic(null);
      setText(null);
    }
    else{
      final String shiftText = formatJobShift(jobShift);
      setText(shiftText);

      // When clicked on, the cell toggles between showing the job shift info or not
      setOnMouseClicked(new EventHandler<MouseEvent>(){
        private boolean isExpanded = false;
        private final String infoText = "\nInfo:\n" + jobShift.getInfo();

        @Override
        public void handle(MouseEvent event) {
          if(!isExpanded)
            setText(shiftText + infoText);
          else
            setText(shiftText);
            isExpanded = !isExpanded;
        }
      });
    }
  }


  //Kan eventuelt bruke toString i core klassen, men da vil ikke toStringen bruke info attributten
  //String representation of the job shift, which is used by the cell
  private String formatJobShift(JobShift jobShift){
    String s = "";
    if (jobShift.getUser()!=null) s += jobShift.getUser().toString() + "\t";
    LocalDateTime startingDateTime = jobShift.getStartingTime();
    s += startingDateTime.getYear() + "-" + formatNumber(startingDateTime.getMonthValue()) + "-" + 
      formatNumber(startingDateTime.getDayOfMonth()) + "\t";
    LocalDateTime endingDateTime = jobShift.getEndingTime();
    s += formatNumber(startingDateTime.getHour()) + ":" + formatNumber(startingDateTime.getMinute()) + " - " + 
    formatNumber(endingDateTime.getHour()) + ":" + formatNumber(endingDateTime.getMinute());
    return s;
  }

  private String formatNumber(int number){
    String s = String.valueOf(number);
    if(number < 10)
      s = "0" + s;
    return s;
  }
}