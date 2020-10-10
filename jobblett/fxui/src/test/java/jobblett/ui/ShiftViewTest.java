package jobblett.ui;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.Duration;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import jobblett.core.Group;
import jobblett.core.JobShift;
import jobblett.core.Main;
import jobblett.core.User;

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
    // Midlertidig
    controller.setMain(getMain());
  }

  // Midlertidig
  private Main getMain() {
    Main main = new Main();
    User user = new User("CorrectUsername", "CorrectPassword12345", "Ole", "Dole");
    Group group = main.getGroupList().newGroup("Test Group");
    group.addUser(user);
    JobShift jobShift = new JobShift(user, LocalDateTime.now().plusHours(2), Duration.ofHours(5), "Test info");
    group.addJobShift(jobShift, user);
    main.getUserList().addUser(user);
    main.setActiveGroup(group);
    main.logIn(user);
    return main;
  }

  @Test
  public void testController() {
    assertNotNull(controller);
  }

  // Vil gi failure
  @Test
  public void testJobShiftsView_correctText() {
    // Denne strengen er kun en placeholder, vi er ikke helt sikre på hva riktig streng er ennå
    String correctJobShiftString = "Correct string";
    ListView<Text> shifts = lookup("#shifts").query();
    assertNotNull(shifts.getItems().stream().
    filter(text -> text.getText().equals(correctJobShiftString)).findFirst().orElse(null));
  }

  @Test
  public void testJobShiftsView_isSorted() {
  }


}