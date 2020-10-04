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
    Button createShiftButton;

    // Må fikse teksten på FXML
    // Vi burde gjøre noe for å generalisere listView koden, nå skriver vi ca. samme
    // kode hver gang vi skal vise noe med listView
    // Har bare lagt inn en dato greie på UI en så man kan ikke ha et skift som går fra 23:00 - 07:00 f.eks

    @Override
    public void update() {
        // Lists all members
        for (User user : activeGroup)
            members.getItems().add(user);
    }

    @FXML
    public void goBack() throws IOException {
        changeScreen("ShiftView.fxml", goBackButton, main);
    }

    @FXML
    public void createShift() throws IOException {
        try{
            User user = members.getSelectionModel().getSelectedItem();
            String info = infoArea.getText(); 
            LocalDateTime startingTime = getStartingTime(date.getValue(), fromField.getText());
            Duration duration = getDuration(fromField.getText(), toField.getText());
            JobShift newShift = new JobShift(user, startingTime, duration, info);
            activeGroup.addJobShift(newShift, activeUser);
            goBack();
        } catch(Exception e){
            e.printStackTrace();
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
            return null;
        }
    }
}
