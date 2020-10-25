package jobblett.ui;

import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import jobblett.core.JobShift;
import jobblett.core.User;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class UpdateShiftController extends SceneController {

  public final static DateTimeFormatter EXPECTED_TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");

  @FXML
  ListView<User> members;

  @FXML
  DatePicker date;

  @FXML
  TextField fromField;

  @FXML
  TextField toField;

  @FXML
  TextArea infoArea;

  @FXML
  Button goBackButton;

  @FXML
  Button createShiftButton;

  @FXML
  Text errorMessage;

  private JobShift activeJobShift;

  // Vi burde gjøre noe for å generalisere listView koden, nå skriver vi ca. samme
  // kode hver gang vi skal vise noe med listView

  @Override
  public void onSceneDisplayed() {
    // Lists all members
    members.getItems().clear();
    for (User user : mainController.getActiveGroup())
      members.getItems().add(user);
    errorMessage.setText("");


    if (activeJobShift == null) {
      // Create new JobShift
      fromField.setText("");
      toField.setText("");
      date.setValue(LocalDate.now());
      infoArea.setText("");
      createShiftButton.setText("Create shift");
    } else {
      // Update existing JobShift
      if (activeJobShift.getUser() != null) members.getSelectionModel().select(activeJobShift.getUser());
      String fromTime = activeJobShift.getStartingTime().format(UpdateShiftController.EXPECTED_TIME_FORMAT);
      String toTime = activeJobShift.getEndingTime().format(UpdateShiftController.EXPECTED_TIME_FORMAT);
      date.setValue(activeJobShift.getStartingTime().toLocalDate());
      fromField.setText(fromTime);
      toField.setText(toTime);
      infoArea.setText(activeJobShift.getInfo());
      createShiftButton.setText("Update shift");
    }
  }

  @FXML
  public void goBack() throws IOException {
    activeJobShift = null;
    mainController.setScene(App.SHIFT_VIEW_ID);
  }

  @FXML
  public void createShift() {
    try {
      User user = members.getSelectionModel().getSelectedItem();
      String info = infoArea.getText();
      LocalDateTime startingTime = getStartingTime(date.getValue(), fromField.getText());
      Duration duration = getDuration(fromField.getText(), toField.getText());
      if (activeJobShift == null) {
        // New JobShift
        activeJobShift = new JobShift(user, startingTime, duration, info);
        mainController.getActiveGroup().addJobShift(activeJobShift, mainController.getActiveUser());
      }
      else {
        // Updating existing JobShift
        activeJobShift.setUser(user);
        activeJobShift.setStartingTime(startingTime);
        activeJobShift.setDuration(duration);
        activeJobShift.setInfo(info);
      }
      goBack();
    } catch (Exception e) {
      errorMessage.setText(e.getMessage());
    }
  }

  private LocalDateTime getStartingTime(LocalDate date, String timeString) {
    return LocalDateTime.of(date, stringToTime(timeString));
  }

  private Duration getDuration(String from, String to) {
    return Duration.between(stringToTime(from), stringToTime(to));
  }

  private LocalTime stringToTime(String timeString) {
    try {
      int splitIndex = timeString.indexOf(":");
      int hour = Integer.parseInt(timeString.substring(0, splitIndex));
      int minute = Integer.parseInt(timeString.substring(splitIndex + 1));
      return LocalTime.of(hour, minute);
    } catch (Exception e) {
      throw new IllegalArgumentException("Time period is not written in the correct format");
    }
  }

  protected void setActiveJobShift(JobShift activeJobShift) {
    this.activeJobShift = activeJobShift;
  }
}
