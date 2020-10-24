package jobblett.ui;

import org.junit.jupiter.api.Test;

import jobblett.core.Group;
import jobblett.core.User;


public class UserHomeControllerTest extends JobbLettTest {

  @Override
  protected String giveID() {
    return App.USER_HOME_ID;
  }

  @Override
  protected User giveActiveUser(){
    return user1;
  }

  @Override
  protected Group giveActiveGroup(){
    return null;
  }

  @Test
  public void testGoTo_GroupHome(){
    
  }
}