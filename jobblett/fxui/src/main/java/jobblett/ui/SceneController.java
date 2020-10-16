package jobblett.ui;

import jobblett.core.Main;

//Code is inspired by: https://github.com/acaicedo/JFX-MultiScreen/tree/master/ScreensFramework/src/screensframework

//super class for scene controllers
public abstract class SceneController {

  protected MainController mainController;
  protected Main main;

  public void setMainController(MainController mainController) {
    this.mainController = mainController;
    main = mainController.getMain();
  }

  //Denne metoden kan droppes hvis vi bruker listeners istedet tror jeg
  public void onSceneDisplayed(){

  }
}