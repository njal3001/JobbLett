package jobblett.ui;

import static jobblett.ui.JobblettScenes.GROUP_HOME;
import static jobblett.ui.JobblettScenes.UPDATE_SHIFT;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

  // TODO: hvilket shift er definert ut i fra indeks, vet ikke
  // om dette vil funke
  @FXML ListView<Integer> shiftsInfo;

  @FXML Button backToGroup;

  @FXML Button newShiftButton;

  @FXML Button editShiftButton;

  @FXML Button deleteShiftButton;


  @FXML CheckBox toggleUserFilterCheckBox;

  @FXML public void initialize() {
    shiftsInfo.setCellFactory(shifts -> new JobShiftListCell(getControllerMap()));
    shiftsInfo.getSelectionModel().selectedItemProperty().addListener(listener -> updateButtons());
  }

  @Override public void onSceneDisplayed() {
    // Sets group name on top of the screen
    groupName.setText(getAccess().getGroupName(getActiveGroupId()));
    toggleUserFilterCheckBox.setSelected(false);
    updateView();
    updateButtons();
    setButtonVisibility();

    //Deletes outdated shifts:

    //Litt vanskelig å itere gjennom listen, mens man sletter elementer...
    int index = 0;
    for (int i = 0; i < getAccess().getJobShiftsSize(getActiveGroupId()); i++) {
      if(getAccess().jobShiftIsOutdated(getActiveGroupId(), index)) {
        getAccess().deleteJobShift(getActiveGroupId(), index);
        index--;
      }
      index++;
    }
  }

  private void setButtonVisibility() {
    List<Button> buttons = List.of(newShiftButton, editShiftButton, deleteShiftButton);
    boolean visible = getAccess().isGroupAdmin(getActiveGroupId(), getActiveUsername());
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
    int selectedJobShiftIndex = shiftsInfo.getSelectionModel().getSelectedIndex();
    SceneController sceneController = getControllerMap().getController(UPDATE_SHIFT);
    UpdateShiftController newController = (UpdateShiftController) sceneController;
    newController.setActiveJobShiftIndex(selectedJobShiftIndex);
    switchScene(UPDATE_SHIFT);
  }

  /**
   * TODO.
   */
  @FXML public void handleDeleteShift() {
    int index = shiftsInfo.getSelectionModel().getSelectedIndex();
    if (index != - 1) {
      getAccess().deleteJobShift(getActiveGroupId(), index);
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
      updateView(getActiveUsername());
    } else {
      updateView();
    }
  }

  // Lists all job shifts
  private void updateView() {
    shiftsInfo.getItems().clear();
    for (int i = 0; i < getAccess().getJobShiftsSize(getActiveGroupId()); i++) {
        shiftsInfo.getItems().add(i);
    }
  }

  private void updateView(String username) {
    List<Integer> shiftIndexes = getAccess().getJobShiftIndexes(getActiveGroupId(), username);
    shiftsInfo.getItems().clear();
    for (int shiftIndex : shiftIndexes) {
        shiftsInfo.getItems().add(shiftIndex);
    }
  }

  private void updateButtons() {
    boolean disable = shiftsInfo.getSelectionModel().getSelectedIndex() == -1;
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
