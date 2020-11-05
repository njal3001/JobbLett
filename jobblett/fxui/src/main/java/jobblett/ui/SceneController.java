package jobblett.ui;


//Code is inspired by: https://github.com/acaicedo/JFX-MultiScreen/tree/master/ScreensFramework/src/screensframework


import javafx.stage.Stage;
import jobblett.core.Group;
import jobblett.core.User;

//super class for scene controllers
public abstract class SceneController {

  private ControllerMap controllerMap;

  public Stage getStage() {
    return controllerMap.getStage();
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

  protected JobblettAccess getAccess() {
    return controllerMap.getAccess();
  }

  public void switchScene(final JobblettScenes jobblettScenes) {
    getControllerMap().switchScene(jobblettScenes);
  }

  protected void setControllerMap(ControllerMap controllerMap) {
    this.controllerMap = controllerMap;
  }

  protected ControllerMap getControllerMap() {
    return controllerMap;
  }

  public void onSceneDisplayed() {
  }

  public void styleIt() {

  }
}
