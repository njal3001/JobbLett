package jobblett.ui;

import static jobblett.ui.JobblettScenes.GROUP_HOME;

import javafx.scene.control.ListCell;

/**
 * CellFactory for the listView showing your groups in UserHomeController.
 */
public class GroupListCell extends ListCell<Integer> {

  private ControllerMap controllerMap;

  public GroupListCell(ControllerMap controllerMap) {
    this.controllerMap = controllerMap;
  }

  // TODO: Skrive javadoc
  @Override public void updateItem(Integer groupId, boolean empty) {
    super.updateItem(groupId, empty);
    if (empty) {
      setGraphic(null);
      setText(null);
    } else {
      setText(controllerMap.getAccess().getGroupName(groupId));
      setOnMouseClicked((event) -> {
        controllerMap.setActiveGroupId(groupId);
        controllerMap.switchScene(GROUP_HOME);
      });
    }
  }
}
