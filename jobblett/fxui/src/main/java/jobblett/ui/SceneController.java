package jobblett.ui;


//Code is inspired by: https://github.com/acaicedo/JFX-MultiScreen/tree/master/ScreensFramework/src/screensframework

import javafx.stage.Stage;

//super class for scene controllers
public abstract class SceneController {

  private ControllerMap controllerMap;


  public Stage getStage() {
    return controllerMap.getStage();
  }

  public String getActiveUsername() {
    return getControllerMap().getActiveUsername();
  }

  public void setActiveUsername(String activeUsername) {
    getControllerMap().setActiveUsername(activeUsername);
  }

  public int getActiveGroupId() {
    return getControllerMap().getActiveGroupId();
  }

  public void setActiveGroupId(int activeGroupId) {
    getControllerMap().setActiveGroupId(activeGroupId);
  }

  protected WorkspaceAccess getAccess() {
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
