package jobblett.ui;

import static jobblett.ui.JobblettScenes.GROUP_HOME;
import static jobblett.ui.JobblettScenes.UPDATE_SHIFT;

import java.time.LocalDateTime;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import jobblett.core.JobShift;
import jobblett.core.User;


public class ShiftViewController extends SceneController {

  @FXML Label groupName;

  @FXML ListView<JobShift> shifts;

  @FXML Button backToGroup;

  @FXML Button newShiftButton;

  @FXML Button editShiftButton;

  @FXML Button deleteShiftButton;


  @FXML CheckBox toggleUserFilterCheckBox;

  @FXML public void initialize() {
    shifts.setCellFactory(shifts -> new JobShiftListCell());
    shifts.getSelectionModel().selectedItemProperty().addListener(listener -> updateButtons());
  }

  @Override public void onSceneDisplayed() {
    // Sets group name on top of the screen
    groupName.setText(getActiveGroup().getGroupName());
    toggleUserFilterCheckBox.setSelected(false);
    updateView();
    updateButtons();
    setButtonVisibility();
  }

  private void setButtonVisibility() {
    List<Button> buttons = List.of(newShiftButton, editShiftButton, deleteShiftButton);
    boolean visible = getActiveGroup().isAdmin(getActiveUser());
    for (Button button : buttons) {
      button.setVisible(visible);
    }
  }

  @FXML public void backButton() {
    switchScene(GROUP_HOME);
  }

  @FXML public void goToCreateShift() {
    switchScene(UPDATE_SHIFT);
  }

  /**
   * TODO.
   */
  @FXML public void goToEditShift() {
    JobShift selectedJobShift = shifts.getSelectionModel().getSelectedItem();
    SceneController sceneController = getControllerMap().getController(UPDATE_SHIFT);
    UpdateShiftController newController = (UpdateShiftController) sceneController;
    newController.setActiveJobShift(selectedJobShift);
    switchScene(UPDATE_SHIFT);
  }

  /**
   * TODO.
   */
  @FXML public void handleDeleteShift() {
    int index = shifts.getSelectionModel().getSelectedIndex();
    JobShift selectedJobShift = shifts.getItems().get(index);
    if (selectedJobShift != null) {
      getActiveGroup().getJobShiftList().remove(selectedJobShift);
      updateView();
    }
  }

  /**
   * TODO.
   *
   * @param event TODO
   */
  @FXML public void toggleUserFilter(ActionEvent event) {
    CheckBox checkBox = (CheckBox) event.getSource();
    if (checkBox.isSelected()) {
      updateView(getActiveUser());
    } else {
      updateView();
    }
  }

  // Burde kanskje bruke observable for å kalle på denne metoden
  // Lists all job shifts
  private void updateView() {
    //Endre metode navn kanskje?
    updateView(getActiveGroup().getJobShiftList().getJobShifts());
  }

  private void updateView(User user) {
    updateView(getActiveGroup().getJobShiftList().getJobShifts(user));
  }

  private void updateView(List<JobShift> shifts) {
    this.shifts.getItems().clear();
    for (JobShift shift : shifts) {
      if (shift.getEndingTime().isAfter(LocalDateTime.now())) {
        this.shifts.getItems().add(shift);
      }
    }
  }

  private void updateButtons() {
    boolean disable = shifts.getSelectionModel().getSelectedIndex() == -1;
    editShiftButton.setDisable(disable);
    deleteShiftButton.setDisable(disable);
  }

  @Override public void styleIt() {
    super.styleIt();
    newShiftButton.setSkin(new ButtonAnimationSkin(newShiftButton));
    editShiftButton.setSkin(new ButtonAnimationSkin(editShiftButton));
    deleteShiftButton.setSkin(new ButtonAnimationSkin(deleteShiftButton));
    backToGroup.setSkin(new ButtonAnimationSkin(backToGroup));
  }
}
