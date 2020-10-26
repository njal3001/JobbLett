package jobblett.ui;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jobblett.core.*;

//Code is inspired by: https://github.com/acaicedo/JFX-MultiScreen/tree/master/ScreensFramework/src/screensframework

//This controller changes the screen in the app
public class MainController {

  private Stage stage;

  private Map<String, Scene> scenes = new HashMap<String, Scene>();
  private Map<String, SceneController> sceneControllers = new HashMap<String, SceneController>();

  private UserList userList;
  private GroupList groupList;
  private User activeUser;
  private Group activeGroup;


  public MainController(Stage stage) {
    this.stage = stage;
  }

  public Stage getStage(){
    return stage;
  }

  public void setUserList(UserList userList) {
    this.userList = userList;
  }

  public UserList getUserList(){
    return userList;
  }

  public void setGroupList(GroupList groupList) {
    GroupList newGroupList = new GroupList();
    for (Group group : groupList) {
      // Remove duplicate users and replace with original users
      Collection<User> duplicateUsers = new ArrayList<>();
      for (User user : group) {
        if (userList.getUser(user.getUserName()) != null)
          duplicateUsers.add(user);
        else
          System.out.println("The user " + user.toString() + " is not contained in userList but contained in " + group.toString() + ".");
      }
      for (User user : duplicateUsers) {
        User originalUser = userList.getUser(user.getUserName());
        group.removeUser(user);
        group.addUser(originalUser);
      }

      // Remove duplicate users in JobShifts
      for (JobShift shift : group.getJobShifts()) {
        User user = shift.getUser();
        if (user == null) break;
        User originalUser = userList.getUser(user.getUserName());
        if (originalUser != null)
          shift.setUser(originalUser);
        else
          System.out.println("The user " + user.toString() + " is not contained in userList but contained in " + shift.toString() + ".");
      }
      newGroupList.addGroup(group);
    }
    this.groupList = newGroupList;
  }

   public GroupList getGroupList() {
    return groupList;
  }

  public void setActiveUser(User activeUser) {
    this.activeUser = activeUser;
  }

  public User getActiveUser() {
    return activeUser;
  }

  public void setActiveGroup(Group activeGroup) {
    this.activeGroup = activeGroup;
  }

  public Group getActiveGroup() {
    return activeGroup;
  }

  public Scene getScene(String name) {
    return scenes.get(name);
  }

  public void loadScene(String name, String resource) throws IOException {
      FXMLLoader loader = new FXMLLoader(getClass().getResource(resource));
      Parent parent = loader.load();
      Scene scene = new Scene(parent);
      SceneController sceneController = ((SceneController) loader.getController());
      sceneController.setMainController(this);
      scenes.put(name, scene);
      sceneControllers.put(name, sceneController);
  }

  public void setScene(final String name) {
    if (scenes.get(name) != null){
      stage.setScene(scenes.get(name));
      getSceneController(name).onSceneDisplayed();
    }
  }

  public void unloadScreen(String name) {
    scenes.remove(name);
    sceneControllers.remove(name);
  }

  public SceneController getSceneController(String name) {
    return sceneControllers.get(name);
  }
}