package jobblett.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.testfx.api.FxRobot;

import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

// Helper class for assertions that are useful for multiple test classes
public class UIAssertions extends FxRobot {

  private MainController mainController;

  public UIAssertions(MainController mainController) {
    this.mainController = mainController;
  }

  public void assertText(String fxid, String expected) {
    Text text = lookup("#" + fxid).query();
    assertEquals(expected, text.getText());
  }

  public void assertListViewHasItem(String fxid, Object expected) {
    ListView<Object> listView = lookup("#" + fxid).query();
    assertTrue(listView.getItems().contains(expected));
  }

  public void assertOnScene(String sceneID) {
    assertEquals(mainController.getScene(sceneID), mainController.getStage().getScene());
  }

  // Assumes only one ListView in scene
  @SuppressWarnings("unchecked")
  public ListCell<Object> findListCell(int index) {
    for (Node node : lookup(".list-cell").queryAll()) {
      if (node instanceof ListCell) {
        ListCell<Object> listCell = (ListCell<Object>) node;
        if (!listCell.isEmpty() && index-- == 0)
        return listCell;
      }
    }
    fail("ListCell was not found");
    return null;
  }
}