package jobblett.ui;

import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import jobblett.core.JobShift;
import jobblett.core.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class CreateShiftController extends AbstractController {

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
    Button newShiftButton;

    @FXML
    Button editShiftButton;

    @FXML
    Button deleteShiftButton;
    
    @FXML
    Text errorMessage;

    // Vi burde gjøre noe for å generalisere listView koden, nå skriver vi ca. samme
    // kode hver gang vi skal vise noe med listView

    @Override
    public void update() {
        // Lists all members
        for (User user : getActiveGroup())
            members.getItems().add(user);
    }

    @FXML
    public void goBack() throws IOException {
        changeScreen(new FXMLLoader(getClass().getResource("ShiftView.fxml")), goBackButton, main);
    }

    @FXML
    public void createShift() throws IOException {
        try{
            User user = members.getSelectionModel().getSelectedItem();
            String info = infoArea.getText(); 
            LocalDateTime startingTime = getStartingTime(date.getValue(), fromField.getText());
            Duration duration = getDuration(fromField.getText(), toField.getText());
            JobShift newShift = new JobShift(user, startingTime, duration, info);
            getActiveGroup().addJobShift(newShift, getLoggedIn());
            goBack();
        } catch(Exception e){
            errorMessage.setText(e.getMessage());
        }
    }

    private LocalDateTime getStartingTime(LocalDate date, String timeString){
        return LocalDateTime.of(date, stringToTime(timeString));
    }

    private Duration getDuration(String from, String to){
        return Duration.between(stringToTime(from), stringToTime(to));
    }

    private LocalTime stringToTime(String timeString){
        try{
            int splitIndex = timeString.indexOf(":");
            int hour = Integer.parseInt(timeString.substring(0, splitIndex));
            int minute = Integer.parseInt(timeString.substring(splitIndex + 1));
            return LocalTime.of(hour, minute);
        } catch(Exception e){
            throw new IllegalArgumentException("Time period is not written on the correct format");
        }
    }
}
