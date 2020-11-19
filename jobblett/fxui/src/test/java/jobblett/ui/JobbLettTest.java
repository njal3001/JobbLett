package jobblett.ui;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import jobblett.ui.ControllerMap;
import jobblett.ui.DirectWorkspaceAccess;
import jobblett.ui.JobblettScenes;
import jobblett.ui.SceneController;
import jobblett.ui.UIAssertions;
import jobblett.ui.WorkspaceAccess;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.stage.Stage;
import jobblett.core.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

//Abstract class which all other UI test classes inherit from
public abstract class JobbLettTest extends ApplicationTest {

  protected User user1, user2;
  protected Group group1, group2;
  protected JobShift jobShift1, jobShift2, jobShift3, newShift;;


  protected SceneController controller;

  protected ControllerMap controllerMap;

  protected UIAssertions uiAssertions;

  //TODO find a better name, we are not calling them ID
  // Subclasses implement this method to give the scene ID
  // for the starting scene of the test
  // TODO dumt å ha denne public?
  public abstract JobblettScenes giveId();

  // Subclasses implement these methods to give the active user and group
  // for the starting scene of the test
  // TODO dumt å ha disse public?
  protected User optionalActiveUser() {
    return null;
  }
  protected Group optionalActiveGroup(){
    return null;
  }

  // Todo maybe make it private
  public Workspace workspace;
  private UserList userList;
  private GroupList groupList;

  @BeforeEach
  // TODO dumt å ha den public?
  public void setUp() {
    uiAssertions = new UIAssertions(controllerMap);
  }

  @Override
  public void start(final Stage primaryStage) throws Exception {
    workspace = new Workspace();
    controllerMap = new ControllerMap(primaryStage,
         new DirectWorkspaceAccess(workspace));

    setupData();
    if(optionalActiveUser() != null) {
      controllerMap.setActiveUsername(optionalActiveUser().getUsername());
    }
    if(optionalActiveGroup() != null) {
      controllerMap.setActiveGroupId(optionalActiveGroup().getGroupId());
    }
    controllerMap.switchScene(giveId());
    primaryStage.show();
    controller = controllerMap.getController(giveId());
  }

  public WorkspaceAccess getAccess() {
    return controllerMap.getAccess();
  }

  public void setupData() {
    userList = workspace.getUserList();
    groupList = workspace.getGroupList();
    user1 = new User("CorrectUsername", new HashedPassword("CorrectPassword12345"), "Ole", "Dole");
    user2 = new User("CorrectUsername2", new HashedPassword("CorrectPassword12345"), "Hans", "Henrik");
    userList.add(user1);
    userList.add(user2);
    group1 = groupList.newGroup("Test Group 1");
    group2 = groupList.newGroup("Test Group 2");
    group1.addUser(user1);
    group1.addAdmin(user1);
    group1.addUser(user2);
    jobShift1 = new JobShift(user1, LocalDateTime.now().plusDays(2), Duration.ofHours(5), "Tester jobshift1");
    jobShift2 = new JobShift(user1, LocalDateTime.now().plusHours(2), Duration.ofHours(5), "Tester jobshift2");
    jobShift3 = new JobShift(user2, LocalDateTime.now().plusDays(3), Duration.ofHours(5), "Tester jobshift3");
    LocalDateTime dateTime = LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(15, 0));
    newShift = new JobShift(user1, dateTime, Duration.ofHours(2), "New shift");
    group1.addJobShift(jobShift1,user1);
    group1.addJobShift(jobShift2, user1);
    group1.addJobShift(jobShift3, user1);
  }

  protected ControllerMap getControllerMap() {
    return controllerMap;
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
