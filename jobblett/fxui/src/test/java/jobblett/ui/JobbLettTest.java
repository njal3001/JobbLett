package jobblett.ui;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.stage.Stage;
import jobblett.core.*;

//Abstract class which all other UI test classes inherit from
public abstract class JobbLettTest extends ApplicationTest {

  protected User user1, user2;
  protected Group group1, group2;
  protected JobShift jobShift1, jobShift2;
  protected MainController mainController;

  protected SceneController controller;

  protected UIAssertions uiAssertions;

  // Subclasses implement this method to give the scene ID
  // for the starting scene of the test
  protected abstract String giveID();

  // Subclasses implement these methods to give the active user and group
  // for the starting scene of the test
  protected abstract User giveActiveUser();
  protected abstract Group giveActiveGroup();

 
  private UserList userList;
  private GroupList groupList;

  @Override
  public void start(final Stage primaryStage) throws Exception {
    mainController = new MainController(primaryStage);

    setupData();
    mainController.setUserList(userList);
    mainController.setGroupList(groupList);
    mainController.setActiveUser(giveActiveUser());
    mainController.setActiveGroup(giveActiveGroup());

    mainController.loadScene(App.LOGIN_ID, App.LOGIN_FILE);
    mainController.loadScene(App.CREATE_USER_ID, App.CREATE_USER_FILE);
    mainController.loadScene(App.USER_HOME_ID, App.USER_HOME_FILE);
    mainController.loadScene(App.JOIN_GROUP_ID, App.JOIN_GROUP_FILE);
    mainController.loadScene(App.CREATE_GROUP_ID, App.CREATE_GROUP_FILE);
    mainController.loadScene(App.GROUP_HOME_ID, App.GROUP_HOME_FILE);
    mainController.loadScene(App.SHIFT_VIEW_ID, App.SHIFT_VIEW_FILE);
    mainController.loadScene(App.UPDATE_SHIFT_ID, App.UPDATE_SHIFT_FILE);

    mainController.setScene(giveID());
    primaryStage.show();
    controller = mainController.getSceneController(giveID());
  }

  protected void setupData() {
    userList = new UserList();
    groupList = new GroupList();
    user1 = new User("CorrectUsername", "CorrectPassword12345", "Ole", "Dole");
    user2 = new User("CorrectUsername2", "CorrectPassword12345", "Hans", "Henrik");
    userList.addUser(user1);
    userList.addUser(user2);
    group1 = groupList.newGroup("Test Group 1");
    group2 = groupList.newGroup("Test Group 2");
    group1.addUser(user1);
    group1.addUser(user2);
  }

  @BeforeEach
  public void setUp(){
    uiAssertions = new UIAssertions(mainController);
  }

  @Test 
  public void testController(){ 
    assertNotNull(controller); 
  }

  @Test
  public void testInitialScene(){
    uiAssertions.assertOnScene(giveID());
  }
}