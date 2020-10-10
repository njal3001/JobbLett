package jobblett.ui;

import static org.junit.jupiter.api.Assertions.assertNotNull;


import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ShiftViewTest extends ApplicationTest{

  private ShiftViewController controller;

  @Override
  public void start(final Stage stage) throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("ShiftView.fxml"));
    Parent root = loader.load();
    controller = loader.getController();
    stage.setScene(new Scene(root));
    stage.show();
    assertNotNull(controller);
  }


  @Test
  public void testController() {
    assertNotNull(controller);
  }

  //To be implemented
  @Test
  public void testJobShiftsView_correctText() {
  }

  @Test
  public void testJobShiftsView_isSorted() {
  }


}