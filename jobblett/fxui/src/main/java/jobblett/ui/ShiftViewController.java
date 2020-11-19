package jobblett.ui;

import static jobblett.ui.JobblettScenes.GROUP_HOME;
import static jobblett.ui.JobblettScenes.UPDATE_SHIFT;

import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;


public class ShiftViewController extends SceneController {

  @FXML Label groupName;

  // TODO: hvilket shift er definert ut i fra indeks, vet ikke
  // om dette vil funke
  @FXML ListView<Integer> shifts;

  @FXML Button backToGroup;

  @FXML Button newShiftButton;

  @FXML Button editShiftButton;

  @FXML Button deleteShiftButton;


  @FXML CheckBox toggleUserFilterCheckBox;

  @FXML public void initialize() {
    shifts.setCellFactory(shifts -> new JobShiftListCell(getControllerMap()));
    shifts.getSelectionModel().selectedItemProperty().addListener(listener -> updateButtons());
  }

  @Override public void onSceneDisplayed() {
    groupName.setText(getAccess().getGroupName(getActiveGroupId()));
    toggleUserFilterCheckBox.setSelected(false);
    updateView();
    updateButtons();
    setButtonVisibility();

    //Deletes outdated shifts:

    //TODO: Litt vanskelig å itere gjennom listen, mens man sletter elementer...
    int index = 0;
    for (int i = 0; i < getAccess().getJobShiftsSize(getActiveGroupId()); i++) {
      if (getAccess().jobShiftIsOutdated(getActiveGroupId(), index)) {
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
    int selectedJobShiftIndex = shifts.getSelectionModel().getSelectedIndex();
    switchScene(UPDATE_SHIFT);
    SceneController sceneController = getControllerMap().getController(UPDATE_SHIFT);
    UpdateShiftController newController = (UpdateShiftController) sceneController;
    newController.setActiveJobShiftIndex(Integer.valueOf(selectedJobShiftIndex));
    //TODO: quick fix...
    newController.onSceneDisplayed();
  }

  /**
   * TODO.
   */
  @FXML public void handleDeleteShift() {
    int index = shifts.getSelectionModel().getSelectedIndex();
    if (index != - 1) {
      getAccess().deleteJobShift(getActiveGroupId(), index);
      updateView();
    }
  }

  /**
   * TODO.
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
    shifts.getItems().clear();
    for (int i = 0; i < getAccess().getJobShiftsSize(getActiveGroupId()); i++) {
      shifts.getItems().add(i);
    }
  }

  private void updateView(String username) {
    List<Integer> shiftIndexes = getAccess().getJobShiftIndexes(getActiveGroupId(), username);
    shifts.getItems().clear();
    for (int shiftIndex : shiftIndexes) {
      shifts.getItems().add(shiftIndex);
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
