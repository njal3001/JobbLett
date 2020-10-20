package jobblett.ui;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.stage.Stage;
import jobblett.core.Group;
import jobblett.core.JobShift;
import jobblett.core.Main;
import jobblett.core.User;

//Abstract class which all other UI test classes inherit from
public abstract class JobbLettTest extends ApplicationTest {

  protected Main main;
  protected User user1, user2;
  protected Group group1, group2;
  protected JobShift jobShift1, jobShift2;

  protected SceneController controller;

  // Subclasses implement this method to give the scene ID
  // for the starting scene of the test
  protected abstract String giveID();

  private MainController mainController;

  @Override
  public void start(final Stage primaryStage) throws Exception {
    mainController = new MainController(primaryStage);

    setupData();
    mainController.setMain(main);

    mainController.loadScene(App.LOGIN_ID, App.LOGIN_FILE);
    mainController.loadScene(App.CREATE_USER_ID, App.CREATE_USER_FILE);
    mainController.loadScene(App.USER_HOME_ID, App.USER_HOME_FILE);
    mainController.loadScene(App.JOIN_GROUP_ID, App.JOIN_GROUP_FILE);
    mainController.loadScene(App.CREATE_GROUP_ID, App.CREATE_GROUP_FILE);
    mainController.loadScene(App.GROUP_HOME_ID, App.GROUP_HOME_FILE);
    mainController.loadScene(App.SHIFT_VIEW_ID, App.SHIFT_VIEW_FILE);
    mainController.loadScene(App.CREATE_SHIFT_ID, App.CREATE_SHIFT_FILE);

    mainController.setScene(giveID());
    primaryStage.show();
    controller = mainController.getSceneController(giveID());
  }

  protected void setupData() {
    main = new Main();
    user1 = new User("CorrectUsername", "CorrectPassword12345", "Ole", "Dole");
    user2 = new User("CorrectUsername2", "CorrectPassword12345", "Hans", "Henrik");
    main.getUserList().addUser(user1);
    main.getUserList().addUser(user2);
    group1 = main.getGroupList().newGroup("Test Group 1");
    group2 = main.getGroupList().newGroup("Test Group 2");
    group1.addUser(user1);
    group1.addUser(user2);
  }

  @Test 
  public void testController(){ 
    assertNotNull(controller); 
  }
}