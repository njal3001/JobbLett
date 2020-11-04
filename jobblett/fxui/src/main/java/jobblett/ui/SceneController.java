package jobblett.ui;


//Code is inspired by: https://github.com/acaicedo/JFX-MultiScreen/tree/master/ScreensFramework/src/screensframework


import javafx.stage.Stage;
import jobblett.core.Group;
import jobblett.core.User;

//super class for scene controllers
public abstract class SceneController {

  private static Stage stage;
  private static User activeUser;
  private static Group activeGroup;
  private static JobblettAccess access;

  public static Stage getStage() {
    return stage;
  }

  public static void setStage(Stage stage) {
    SceneController.stage = stage;
  }

  public static User getActiveUser() {
    return activeUser;
  }

  public static void setActiveUser(User activeUser) {
    SceneController.activeUser = activeUser;
  }

  public static Group getActiveGroup() {
    return activeGroup;
  }

  public static void setActiveGroup(Group activeGroup) {
    SceneController.activeGroup = activeGroup;
  }

  protected static JobblettAccess getAccess() {
    return access;
  }

  public static void setAccess(JobblettAccess access) {
    SceneController.access = access;
  }

  public static void switchScene(final JobblettScenes jobblettScenes) {
    stage.setScene(jobblettScenes.getScene());
    jobblettScenes.getController().onSceneDisplayed();
  }

  public abstract void onSceneDisplayed();

  public void styleIt() {

  }
}
