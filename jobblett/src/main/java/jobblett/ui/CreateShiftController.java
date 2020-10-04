package jobblett.ui;

import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import jobblett.core.User;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import java.io.IOException;

public class CreateShiftController extends AbstractController{


    @FXML
    ListView<Text> members;

    @FXML
    DatePicker date;

    @FXML
    TextField fromField;

    @FXML
    TextField toField;

    @FXML
    Button goBackButton;
    
    @FXML
    Button createShiftButton;

    //Må fikse teksten på FXML
    // Vi burde gjøre noe for å generalisere listView koden, nå skriver vi ca. samme kode hver gang vi skal vise noe med listView



    @FXML
    public void goBack() throws IOException {
        changeScreen("ShiftView.fxml", goBackButton, main);
    }
    
    @FXML
    public void createShift() throws IOException {
        //.....
        goBack();
    }
}
