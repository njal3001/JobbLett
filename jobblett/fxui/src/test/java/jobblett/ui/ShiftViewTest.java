package jobblett.ui;


import org.junit.jupiter.api.Test;
import javafx.stage.Stage;

public class ShiftViewTest extends JobbLettTest{

  @Override
  protected String giveFxmlFileName() {
    return "ShiftView.fxml";
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