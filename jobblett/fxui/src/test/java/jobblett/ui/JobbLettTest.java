package jobblett.ui;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.stage.Stage;
import jobblett.core.*;

import java.time.Duration;
import java.time.LocalDateTime;

//Abstract class which all other UI test classes inherit from
public abstract class JobbLettTest extends ApplicationTest {

  protected User user1, user2;
  protected Group group1, group2;
  protected JobShift jobShift1, jobShift2, jobShift3;


  protected SceneController controller;

  private ControllerMap controllerMap;

  protected UIAssertions uiAssertions;

  //TODO find a better name, we are not calling them ID
  // Subclasses implement this method to give the scene ID
  // for the starting scene of the test
  protected abstract JobblettScenes giveId();

  // Subclasses implement these methods to give the active user and group
  // for the starting scene of the test
  protected User optionalActiveUser() {
    return null;
  }
  protected Group optionalActiveGroup(){
    return null;
  }

 
  private UserList userList;
  private GroupList groupList;

  @Override
  public void start(final Stage primaryStage) throws Exception {
    controllerMap = App.commonStart(primaryStage);
    setupData();
    getAccess().setLists(userList,groupList);
    setActiveUser(optionalActiveUser());
    setActiveGroup(optionalActiveGroup());
    controllerMap.switchScene(giveId());
    primaryStage.show();
    controller = controllerMap.getController(giveId());
  }

  public JobblettAccess getAccess() {
    return controllerMap.getAccess();
  }

  protected void setupData() {
    userList = new UserList();
    groupList = new GroupList();
    user1 = new User("CorrectUsername", new HashedPassword("CorrectPassword12345"), "Ole", "Dole");
    user2 = new User("CorrectUsername2", new HashedPassword("CorrectPassword12345"), "Hans", "Henrik");
    userList.add(user1);
    userList.add(user2);
    group1 = groupList.newGroup("Test Group 1");
    group2 = groupList.newGroup("Test Group 2");
    group1.addUser(user1);
    group1.addAdmin(user1);
    group1.addUser(user2);
    jobShift1 = new JobShift(user1, LocalDateTime.now().plusHours(5), Duration.ofHours(5), "Tester jobshift1");
    jobShift2 = new JobShift(user1, LocalDateTime.now().plusHours(2), Duration.ofHours(5), "Tester jobshift2");
    jobShift3 = new JobShift(user2, LocalDateTime.now().plusHours(7), Duration.ofHours(5), "Tester jobshift3");
    group1.addJobShift(jobShift1,user1);
    group1.addJobShift(jobShift2, user1);
    group1.addJobShift(jobShift3, user1);
  }

  protected ControllerMap getControllerMap() {
    return controllerMap;
  }

  public User getActiveUser() {
    return getControllerMap().getActiveUser();
  }

  public void setActiveUser(User activeUser) {
    getControllerMap().setActiveUser(activeUser);
  }

  public Group getActiveGroup() {
    return getControllerMap().getActiveGroup();
  }

  public void setActiveGroup(Group activeGroup) {
    getControllerMap().setActiveGroup(activeGroup);
  }

  @BeforeEach
  public void setUp(){
    uiAssertions = new UIAssertions(getControllerMap());
  }

  @Test 
  public void testController(){ 
    assertNotNull(controller); 
  }

  @Test
  public void testInitialScene(){
    uiAssertions.assertOnScene(giveId());
  }
}
