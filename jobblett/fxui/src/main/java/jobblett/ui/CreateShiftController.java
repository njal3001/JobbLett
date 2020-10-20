package jobblett.ui;

import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import jobblett.core.JobShift;
import jobblett.core.User;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class CreateShiftController extends SceneController {

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

  @Override
  public void onSceneDisplayed() {
    // Lists all members
    for (User user : mainController.getActiveGroup()){
      members.getItems().add(user);
    }
  }

  @FXML
  public void goBack() throws IOException {
    mainController.setScene(App.SHIFT_VIEW_ID);
  }

  @FXML
  public void createShift(){
    try {
      User user = members.getSelectionModel().getSelectedItem();
      String info = infoArea.getText();
      LocalDateTime startingTime = getStartingTime(date.getValue(), fromField.getText());
      Duration duration = getDuration(fromField.getText(), toField.getText());
      JobShift newShift = new JobShift(user, startingTime, duration, info);
      mainController.getActiveGroup().addJobShift(newShift, mainController.getActiveUser());
      goBack();
    } catch (Exception e) {
      e.printStackTrace();
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
      return null;
    }
  }
}
