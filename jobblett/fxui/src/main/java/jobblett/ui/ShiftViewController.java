package jobblett.ui;

import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import jobblett.core.JobShift;
import jobblett.core.User;

public class ShiftViewController extends SceneController {

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

  @FXML
  CheckBox toggleUserFilterCheckBox;

  @FXML
  public void initialize(){
    shifts.setCellFactory(shifts -> {
      JobShiftListCell listCell = new JobShiftListCell();
      return listCell;
    });

    shifts.getSelectionModel().selectedItemProperty().addListener(listener -> {
      updateButtons();
    });
  }

  @Override
  public void onSceneDisplayed() {
    // Sets group name on top of the screen
    groupName.setText(mainController.getActiveGroup().getGroupName());
    toggleUserFilterCheckBox.setSelected(false);
    updateView();
    updateButtons();
    setButtonVisibility();
  }

  private void setButtonVisibility(){
    List<Button> buttons = List.of(newShiftButton, editShiftButton, deleteShiftButton);
    boolean visible = mainController.getActiveGroup().isAdmin(mainController.getActiveUser());
    for(Button button : buttons)
      button.setVisible(visible);
  }

  @FXML
  public void backButton() {
    mainController.setScene(App.GROUP_HOME_ID);
  }

  @FXML
  public void goToCreateShift() {
    mainController.setScene(App.UPDATE_SHIFT_ID);
  }

  @FXML
  public void goToEditShift(){
    JobShift selectedJobShift = shifts.getSelectionModel().getSelectedItem();
    UpdateShiftController newController = (UpdateShiftController) mainController.getSceneController(App.UPDATE_SHIFT_ID);
    newController.setActiveJobShift(selectedJobShift);
    mainController.setScene(App.UPDATE_SHIFT_ID);
  }

  @FXML
  public void handleDeleteShift(){
    int index = shifts.getSelectionModel().getSelectedIndex();
    JobShift selectedJobShift = shifts.getItems().get(index);
    if (selectedJobShift != null) {
      mainController.getActiveGroup().getJobShifts().remove(selectedJobShift);
      updateView();
    }
  }

  @FXML
  public void toggleUserFilter(ActionEvent event){
    CheckBox checkBox = (CheckBox) event.getSource();
    if(checkBox.isSelected())
      updateView(mainController.getActiveUser());
    else
      updateView();
  }

  // Burde kanskje bruke observable for å kalle på denne metoden
  // Lists all job shifts
  private void updateView() {
    //Endre metode navn kanskje?
    updateView(mainController.getActiveGroup().getJobShifts().getJobShifts());
  }

  private void updateView(User user) {
    updateView(mainController.getActiveGroup().getJobShifts().getJobShifts(user));
  }

  private void updateView(List<JobShift> shifts){
    this.shifts.getItems().clear();
    for (JobShift shift : shifts)
      this.shifts.getItems().add(shift);
  }

  private void updateButtons() {
    boolean disable = shifts.getSelectionModel().getSelectedIndex() == -1;
    editShiftButton.setDisable(disable);
    deleteShiftButton.setDisable(disable);
  }
}
