package jobblett.ui;

import jobblett.core.Main;

public abstract class ScreenController {

  protected MainController mainController;
  protected Main main;

  public void setMainController(MainController mainController) {
    this.mainController = mainController;
    main = mainController.getMain();
  }

  public void onScreenDisplayed(){

  }
}