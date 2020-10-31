package jobblett.ui;


//Code is inspired by: https://github.com/acaicedo/JFX-MultiScreen/tree/master/ScreensFramework/src/screensframework

//super class for scene controllers
public abstract class SceneController {

  //Klassen har veldig lite funksjonalitet, kan kanskje bruke interface istedet

  protected MainController mainController;

  public void setMainController(MainController mainController) {
    this.mainController = mainController;
  }

  public abstract void onSceneDisplayed();

  protected JobblettAccess getAccess() {
    return mainController.access;
  }
}