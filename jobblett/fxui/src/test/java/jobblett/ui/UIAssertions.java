package jobblett.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import javafx.scene.control.*;
import javafx.scene.text.TextAlignment;
import jobblett.core.JobShift;
import org.assertj.core.internal.bytebuddy.asm.Advice;
import org.testfx.api.FxRobot;

import javafx.scene.Node;
import javafx.scene.text.Text;

import java.time.LocalDateTime;

// Helper class for assertions that are useful for multiple test classes
public class UIAssertions extends FxRobot {

  private MainController mainController;

  public UIAssertions(MainController mainController) {
    this.mainController = mainController;
  }

  public void assertLabel(String fxid, String expected) {
    Label text = lookup("#" + fxid).query();
    assertEquals(expected, text.getText());
  }

  public void assertTextField(String fxid, String expected) {
    TextField textField =  lookup("#" + fxid).query();
    assertEquals(expected, textField.getText());
  }

  public void assertTextArea(String fxid, String expected) {
    TextArea textArea =  lookup("#" + fxid).query();
    assertEquals(expected, textArea.getText());
  }

  public void assertDate(String fxid, String expected) {
    DatePicker date = lookup("#" + fxid).query();
    assertEquals(expected, date.getValue().format(App.EXPECTED_DATE_FORMAT));
  }

  public void asserListView(String fxid, String expected) {
    ListView listView = lookup("#" + fxid).query();
    assertEquals(expected, listView.toString());
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