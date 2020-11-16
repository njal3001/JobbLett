package jobblett.ui;

import javafx.scene.control.ListCell;
import javafx.scene.text.Font;

public class UserListCell extends ListCell<String> {
  ControllerMap controllerMap;

  public UserListCell(ControllerMap controllerMap) {
    this.controllerMap = controllerMap;
  }

  @Override public void updateItem(String username, boolean empty) {
    super.updateItem(username, empty);
    WorkspaceAccess access = controllerMap.getAccess();
    if (empty || username == null) {
      setGraphic(null);
      setText(null);
    } else if (access.isGroupAdmin(controllerMap.getActiveGroupId(), username)) {
      setText(access.getUserToString(username) + " [Admin]");
      setFont(Font.loadFont(ButtonAnimationSkin.class.getResourceAsStream(App.BOLD_FONT_FILE), 16));
    } else {
      setText(access.getUserToString(username));
    }
  }
}
