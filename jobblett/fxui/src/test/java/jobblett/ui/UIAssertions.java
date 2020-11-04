package jobblett.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import javafx.scene.control.*;
import org.testfx.api.FxRobot;

import javafx.scene.Node;

// Helper class for assertions that are useful for multiple test classes
public class UIAssertions extends FxRobot {

  public void assertLabel(String fxid, String expected) {
    Label text = lookup("#" + fxid).query();
    assertEquals(expected, text.getText(),"The Label: '"+fxid+"' didn't have the expected value: "+expected);
  }

  public void assertTextField(String fxid, String expected) {
    TextField textField =  lookup("#" + fxid).query();
    assertEquals(expected, textField.getText(), "The TextField: '"+fxid+"' didn't have the expected value: "+expected);
  }

  public void assertTextArea(String fxid, String expected) {
    TextArea textArea =  lookup("#" + fxid).query();
    assertEquals(expected, textArea.getText(), "The TextArea: '"+fxid+"' didn't have the expected value: "+expected);
  }

  public void assertDate(String fxid, String expected) {
    DatePicker date = lookup("#" + fxid).query();
    assertEquals(expected, date.getValue().format(App.EXPECTED_DATE_FORMAT), "The DateField: '"+fxid+"' didn't have the expected value or format: "+expected);
  }

  /*//Fjenre? denne brukes ikke av noen??
  public void asserListViewItemHasText(String fxid,Object item, String expected) {
    ListView listView = lookup("#" + fxid).query();
    assertEquals(expected, listView.toString());
  }*/


  public void assertListViewHasItem(String fxid, Object expected) {
    ListView<Object> listView = lookup("#" + fxid).query();
    assertTrue(listView.getItems().contains(expected), "The ListView: '"+fxid+"' didn't have the expected object.");
  }

  public void assertListViewHasNotItem(String fxid, Object unexpected) {
    ListView<Object> listView = lookup("#" + fxid).query();
    assertFalse(listView.getItems().contains(unexpected), "The ListView: '"+fxid+"' did have the unexpected object.");
  }

  public void assertOnScene(JobblettScenes sceneID) {
    assertEquals(sceneID.getScene(), SceneController.getStage().getScene());
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

  public void assertListCellText(int index, String expectedString) {
    ListCell<Object> selectedCell= findListCell(index);
    String stringShownOnCell= selectedCell.getText();
    assertEquals(expectedString, stringShownOnCell,"The string shown in the cell is not the same as expected");
  }
}