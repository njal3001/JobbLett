package jobblett.ui;


import org.junit.jupiter.api.Test;
import javafx.stage.Stage;

public class ShiftViewTest extends JobbLettTest{

  @Override
  public void start(final Stage stage) throws Exception {
    fxmlFileName = "ShiftView.fxml";
    super.start(stage);
  }

  @Override
  protected void setupData(){
    super.setupData();
    main.logIn(user1);
    main.setActiveGroup(group1);
    // ....
  }

  //To be implemented
  @Test
  public void testJobShiftsView_correctText() {
  }

  @Test
  public void testJobShiftsView_isSorted() {
  }
}