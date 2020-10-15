package jobblett.ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import jobblett.core.JobShift;

public class ShiftViewController extends AbstractController {

  //Ødela UIen når jeg la til nye knapper, må fikses

  @FXML
  Text groupName;

  @FXML
  ListView<JobShift> shifts;

  @FXML
  Button backToGroup;

  @FXML
  Button newShiftButton;

  @FXML
  Button editShiftButton;

  @FXML
  Button deleteShiftButton;

  @Override
  public void update() {
    // Sets group name on top of the screen
    groupName.setText(getActiveGroup().getGroupName());

    shifts.setCellFactory(shifts -> {
      JobShiftListCell listCell = new JobShiftListCell();
      return listCell;
    });

    shifts.getSelectionModel().selectedItemProperty().
    addListener(listener -> {updateButtons();});
    updateView();
    updateButtons();
    newShiftButton.setVisible(getActiveGroup().isAdmin(getLoggedIn()));
  }

  @FXML
  public void backButton() throws IOException {
    changeScreen(new FXMLLoader(getClass().getResource("GroupHome.fxml")), backToGroup, main);
  }

  @FXML
  public void goToCreateShift() throws IOException {
    changeScreen(new FXMLLoader(getClass().getResource("CreateShift.fxml")), newShiftButton, main);
  }

  @FXML
  public void goToEditShift() throws IOException {
    changeScreen(new FXMLLoader(getClass().getResource("EditShift.fxml")), newShiftButton, main);
  }

  @FXML
  public void handleDeleteShift() throws IOException {
    int index = shifts.getSelectionModel().getSelectedIndex();
    JobShift selectedJobShift = shifts.getItems().get(index);
    if(selectedJobShift != null){
      getActiveGroup().getJobShifts().removeJobShift(selectedJobShift);
      updateView();
    }
  }
  // Burde kanskje bruke observable for å kalle på denne metoden
  // Lists all job shifts
  private void updateView(){
    shifts.getItems().clear();
    for (JobShift shift : getActiveGroup().getJobShifts())
      shifts.getItems().add(shift);
  }

  private void updateButtons(){
    boolean disable = shifts.getSelectionModel().getSelectedIndex() == -1;
    editShiftButton.setDisable(disable);
    deleteShiftButton.setDisable(disable);
  }
}
