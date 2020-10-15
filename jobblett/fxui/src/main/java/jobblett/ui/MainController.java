package jobblett.ui;

import java.util.HashMap;
import java.util.Map;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import jobblett.core.Main;

//Code is from: https://github.com/acaicedo/JFX-MultiScreen/tree/master/ScreensFramework/src/screensframework

//This controller changes the screen in the app
public class MainController extends Pane {

  private Map<String, Node> screens = new HashMap<String, Node>();
  private Map<String, ScreenController> screenControllers = new HashMap<String, ScreenController>();

  private Main main;

  public void setMain(Main main) {
    this.main = main;
  }

  public Main getMain() {
    return main;
  }

  // Add the screen to the collection
  public void addScreen(String name, Node screen) {
    screens.put(name, screen);
  }

  // Returns the Node with the appropriate name
  public Node getScreen(String name) {
    return screens.get(name);
  }

  // Loads the fxml file, add the screen to the screens collection and
  // finally injects the screenPane to the controller.
  public boolean loadScreen(String name, String resource) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource(resource));
      Parent loadScreen = (Parent) loader.load();
      ScreenController screenController = ((ScreenController) loader.getController());
      screenController.setMainController(this);
      addScreen(name, loadScreen);
      screenControllers.put(name, screenController);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  // This method tries to displayed the screen with a predefined name.
  // First it makes sure the screen has been already loaded. Then if there is more
  // than
  // one screen the new screen is been added second, and then the current screen
  // is removed.
  // If there isn't any screen being displayed, the new screen is just added to
  // the root.
  public boolean setScreen(final String name) {
    if (screens.get(name) != null) { // screen loaded
      if (!getChildren().isEmpty()) // if there is more than one screen
        getChildren().remove(0); // remove the displayed screen
      getChildren().add(0, screens.get(name)); // add the screen
      getScreenController(name).onScreenDisplayed();
      return true;
    } else {
      return false;
    }
  }

  // This method will remove the screen with the given name from the collection of
  // screens
  public boolean unloadScreen(String name) {
    if (screens.remove(name) == null || screenControllers.remove(name) == null) {
      return false;
    } else {
      return true;
    }
  }

  public ScreenController getScreenController(String name) {
    return screenControllers.get(name);
  }
}