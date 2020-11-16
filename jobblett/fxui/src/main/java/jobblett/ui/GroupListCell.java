package jobblett.ui;

import static jobblett.ui.JobblettScenes.GROUP_HOME;

import javafx.scene.control.ListCell;
import jobblett.core.Group;

/**
 * CellFactory for the listView showing your groups in UserHomeController.
 */
public class GroupListCell extends ListCell<Group> {

  ControllerMap controllerMap;

  public GroupListCell(ControllerMap controllerMap) {
    this.controllerMap = controllerMap;
  }

//TODO
  @Override public void updateItem(Group group, boolean empty) {
    super.updateItem(group, empty);
    if (empty || group == null) {
      setGraphic(null);
      setText(null);
    } else {
      setText(group.getGroupName());
      setOnMouseClicked((event) -> {
        controllerMap.setActiveGroup(group);
        controllerMap.switchScene(GROUP_HOME);
      });

    }
  }
}
