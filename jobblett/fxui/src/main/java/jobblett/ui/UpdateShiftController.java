package jobblett.ui;

import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import jobblett.core.JobShift;
import jobblett.core.User;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.util.StringConverter;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Observable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdateShiftController extends SceneController {

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

  @FXML
  public void initialize(){
    // setting up dateformat for the datepicker
    date.setConverter(new StringConverter<LocalDate>() {

      @Override
      public String toString(LocalDate date) {
        if (date == null)
          return "";

        return App.EXPECTED_DATE_FORMAT.format(date);
      }

      @Override
      public LocalDate fromString(String dateString) {
        if (dateString == null || dateString.isEmpty())
          return null;

        return LocalDate.parse(dateString, App.EXPECTED_DATE_FORMAT);
      }
    });

    // making it not able to write in date, the user has to use to calender to pick
    // a date
    date.setEditable(false);

    //Det her er litt mye logikk, burde kanskje heller ligge i en core klasse?
    ChangeListener<String> listener = (observable, oldValue, newValue) -> {

      /*
       * String pattern = "^(?=.*[0-9])(?=.*[:]).{0,}$";
       * if(!newValue.matches(pattern)){ if(oldValue.matches(pattern))
       * fromField.setText(oldValue); else fromField.setText("00:00"); }
       */

      if(newValue.length() > 5){
        errorMessage.setText("Time period is not written in the correct format");
        return;
      }

      //Finnes definitivt bedre måter å gjøre dette på
      List<String> patterns = List.of("[0-2]", "[0-9]", ":", "[0-5]", "[0-9]");
 
      for (int i = 0; i < newValue.length(); i++){
        if(!String.valueOf(newValue.charAt(i)).matches(patterns.get(i))){
          errorMessage.setText("Time period is not written in the correct format");
          return;
        }
      }
        errorMessage.setText("");
    };
    //utbedre til å detektere feil inntast automatisk??
    fromField.textProperty().addListener(listener);
    toField.textProperty().addListener(listener);
  }
  

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
      if (activeJobShift.getUser() != null)
        members.getSelectionModel().select(activeJobShift.getUser());
      String fromTime = activeJobShift.getStartingTime().format(App.EXPECTED_TIME_FORMAT);
      String toTime = activeJobShift.getEndingTime().format(App.EXPECTED_TIME_FORMAT);
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
      } else {
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
