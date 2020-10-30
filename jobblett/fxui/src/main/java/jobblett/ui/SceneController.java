package jobblett.ui;


//Code is inspired by: https://github.com/acaicedo/JFX-MultiScreen/tree/master/ScreensFramework/src/screensframework

import javafx.scene.text.Font;

import java.io.IOException;
import java.io.InputStreamReader;


//super class for scene controllers
public abstract class SceneController {

  Font font = Font.loadFont(SceneController.class.getResourceAsStream(App.FONT_FILE),16);

  //Klassen har veldig lite funksjonalitet, kan kanskje bruke interface istedet

  protected MainController mainController;

  public void setMainController(MainController mainController) {
    this.mainController = mainController;
  }

  //Denne metoden kan droppes hvis vi bruker listeners istedet tror jeg
  public void onSceneDisplayed(){
  }

  public void styleIt() {

  }

  protected JobblettAccess getAccess() {
    return mainController.access;
  }
}