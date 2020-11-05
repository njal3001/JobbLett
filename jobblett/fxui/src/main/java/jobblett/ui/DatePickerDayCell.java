package jobblett.ui;

import java.time.LocalDate;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.util.Callback;

public class DatePickerDayCell implements Callback<DatePicker, DateCell> {
  @Override public DateCell call(DatePicker param) {
    return new DateCell() {
      @Override public void updateItem(LocalDate date, boolean empty) {
        super.updateItem(date, empty);
        if (date.isBefore(LocalDate.now())) {
          setDisable(true);
        }
      }
    };
  }


}
