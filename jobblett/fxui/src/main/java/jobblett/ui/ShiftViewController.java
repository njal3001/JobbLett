package jobblett.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import jobblett.core.JobShift;

public class ShiftViewController extends SceneController {

  @FXML
  Label groupName;

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
  public void onSceneDisplayed() {
    // Sets group name on top of the screen
    groupName.setText(mainController.getActiveGroup().getGroupName());

    shifts.setCellFactory(shifts -> {
      JobShiftListCell listCell = new JobShiftListCell();
      return listCell;
    });

    shifts.getSelectionModel().selectedItemProperty().addListener(listener -> {
      updateButtons();
    });
    updateView();
    updateButtons();
    newShiftButton.setVisible(mainController.getActiveGroup().isAdmin(mainController.getActiveUser()));
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

  // Burde kanskje bruke observable for å kalle på denne metoden
  // Lists all job shifts
  private void updateView() {
    shifts.getItems().clear();
    for (JobShift shift : mainController.getActiveGroup().getJobShifts())
      shifts.getItems().add(shift);
  }

  private void updateButtons() {
    boolean disable = shifts.getSelectionModel().getSelectedIndex() == -1;
    editShiftButton.setDisable(disable);
    deleteShiftButton.setDisable(disable);
  }

  @Override
  public void styleIt() {
    super.styleIt();
    newShiftButton.setSkin(new JobblettButtonSkin(newShiftButton));
    newShiftButton.setStyle("-fx-background-color: #a0ffa0;");
    editShiftButton.setSkin(new JobblettButtonSkin(editShiftButton));
    deleteShiftButton.setSkin(new JobblettButtonSkin(deleteShiftButton));
    deleteShiftButton.setStyle("-fx-background-color: #ffa0a0;");
    backToGroup.setSkin(new JobblettButtonSkin(deleteShiftButton));
  }
}
