package jobblett.ui;

import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import jobblett.core.JobShift;
import org.testfx.api.FxRobot;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

// Helper class for assertions that are useful for multiple test classes
public class UIAssertions extends FxRobot {

  ControllerMap controllerMap;

  public UIAssertions(ControllerMap controllerMap) {
    this.controllerMap = controllerMap;
  }

  public void assertLabel(String fxid, String expected) {
    Label text = lookup("#" + fxid).query();
    assertEquals(expected, text.getText(), "The Label: '" + fxid + "' didn't have the expected value: " + expected);
  }

  public void assertTextField(String fxid, String expected) {
    TextField textField = lookup("#" + fxid).query();
    assertEquals(expected, textField.getText(),
        "The TextField: '" + fxid + "' didn't have the expected value: " + expected);
  }

  public void assertTextArea(String fxid, String expected) {
    TextArea textArea = lookup("#" + fxid).query();
    assertEquals(expected, textArea.getText(),
        "The TextArea: '" + fxid + "' didn't have the expected value: " + expected);
  }

  public void assertDate(String fxid, String expected) {
    DatePicker date = lookup("#" + fxid).query();
    assertEquals(expected, date.getValue().format(App.EXPECTED_DATE_FORMAT),
        "The DateField: '" + fxid + "' didn't have the expected value or format: " + expected);
  }

  public void assertListViewHasItem(String fxid, Object expected) {
    ListView<Object> listView = lookup("#" + fxid).query();
    assertTrue(listView.getItems().contains(expected), "The ListView: '" + fxid + "' didn't have the expected object.");
  }

  public void assertListViewHasNotItem(String fxid, Object unexpected) {
    ListView<Object> listView = lookup("#" + fxid).query();
    assertFalse(listView.getItems().contains(unexpected),
        "The ListView: '" + fxid + "' did have the unexpected object.");
  }

  public void assertOnScene(JobblettScenes sceneID) {
    int millisecondTimeout = 2000;
    for (int tenthsMillisecond = 0; tenthsMillisecond < millisecondTimeout/10; tenthsMillisecond ++) {
      if (controllerMap.getScene(sceneID).equals(controllerMap.getStage().getScene())) {
        break;
      }
      try {
        TimeUnit.MILLISECONDS.sleep(10);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    assertEquals(controllerMap.getScene(sceneID), controllerMap.getStage().getScene());
  }

  // Assumes only one ListView in scene
  @SuppressWarnings("unchecked")
  public ListCell<Object> findListCell(int index) {
    for (Node node : lookup(".list-cell").queryAll()) {
      if (node instanceof ListCell) {
        ListCell<Object> listCell = (ListCell<Object>) node;
        if (!listCell.isEmpty() && index-- == 0) {
          return listCell;
        }
      }
    }
    return null;
  }

  public void assertListCellText(int index, String expectedString) {
    ListCell<Object> selectedCell = findListCell(index);
    String stringShownOnCell = selectedCell.getText();
    assertEquals(expectedString, stringShownOnCell, "The string shown in the cell is not the same as expected");
  }

  public void assertBoldText(ListCell<Object> listCell) {
    assertEquals("Bold", listCell.getFont().getStyle());
  }

  public void assertListViewSize(String fxid, int expected) {
    ListView<Object> listView = lookup("#" + fxid).query();
    assertEquals(expected, listView.getItems().size(), "Size of ListView is not correct");
  }

  //ONLY USED BY TESTS WITH JOBSHIFT

  public void assertJobShiftListCellText(JobShift jobShift, int index) {
    assertHasJobShift(jobShift);
    assertListCellText(index, formatJobShift(jobShift));
  }

  public void assertHasJobShift(JobShift jobShift) {
    assertTrue(hasJobShift(jobShift), "No such JobShift in ListView");
  }

  public void assertHasNotJobShift(JobShift jobShift) {
    assertFalse(hasJobShift(jobShift), "Jobshift should not be in ListView");
  }

  private boolean hasJobShift(JobShift jobShift) {
    ListView<Integer> view = lookup("#shifts").query();
    for (int i = 0; i < view.getItems().size(); i++) {
      ListCell<Object> cell = findListCell(i);
      if (cell != null && formatJobShift(jobShift).equals(cell.getText())) {
        return true;
      }
    }
    return false;
  }

  private String formatJobShift(JobShift jobShift) {
    String s = "";
    s += jobShift.getUser().toString() + "\t";
    LocalDateTime startingDateTime = jobShift.getStartingTime();
    s += startingDateTime.format(App.EXPECTED_DATE_FORMAT) + "\t";
    LocalDateTime endingDateTime = jobShift.getEndingTime();
    s += startingDateTime.format(App.EXPECTED_TIME_FORMAT);
    s += " - ";
    s += endingDateTime.format(App.EXPECTED_TIME_FORMAT);
    s += "\nInfo:\n" + jobShift.getInfo();
    return s;
  }
}
