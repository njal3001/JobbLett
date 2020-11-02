package jobblett.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import jobblett.core.JobShift;

import static jobblett.ui.JobblettScenes.*;


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
    groupName.setText(getActiveGroup().getGroupName());

    shifts.setCellFactory(shifts -> new JobShiftListCell());

    shifts.getSelectionModel().selectedItemProperty().addListener(listener -> updateButtons());
    updateView();
    updateButtons();
    newShiftButton.setVisible(getActiveGroup().isAdmin(getActiveUser()));
  }

  @FXML
  public void backButton() {
    switchScene(GROUP_HOME_ID);
  }

  @FXML
  public void goToCreateShift() {
    switchScene(UPDATE_SHIFT_ID);
  }

  @FXML
  public void goToEditShift(){
    JobShift selectedJobShift = shifts.getSelectionModel().getSelectedItem();
    UpdateShiftController newController = (UpdateShiftController) UPDATE_SHIFT_ID.getController();
    newController.setActiveJobShift(selectedJobShift);
    switchScene(UPDATE_SHIFT_ID);
  }

  @FXML
  public void handleDeleteShift(){
    int index = shifts.getSelectionModel().getSelectedIndex();
    JobShift selectedJobShift = shifts.getItems().get(index);
    if (selectedJobShift != null) {
      getActiveGroup().getJobShifts().remove(selectedJobShift);
      updateView();
    }
  }

  // Burde kanskje bruke observable for å kalle på denne metoden
  // Lists all job shifts
  private void updateView() {
    shifts.getItems().clear();
    for (JobShift shift : getActiveGroup().getJobShifts())
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
    newShiftButton.setSkin(new ButtonAnimationSkin(newShiftButton));
    editShiftButton.setSkin(new ButtonAnimationSkin(editShiftButton));
    deleteShiftButton.setSkin(new ButtonAnimationSkin(deleteShiftButton));
    backToGroup.setSkin(new ButtonAnimationSkin(backToGroup));
  }
}
