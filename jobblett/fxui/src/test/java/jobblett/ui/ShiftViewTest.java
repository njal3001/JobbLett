package jobblett.ui;


import org.junit.jupiter.api.Test;

public class ShiftViewTest extends JobbLettTest{

  @Override
  protected String giveID() {
    return App.SHIFT_VIEW_ID;
  }
  
  @Override
  protected void setupData(){
    super.setupData();
    activeUser = user1;
    activeGroup = group1;
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