package jobblett.ui;

import java.util.HashMap;
import java.util.Map;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import jobblett.core.Main;

//Code is inspired by: https://github.com/acaicedo/JFX-MultiScreen/tree/master/ScreensFramework/src/screensframework

//This controller changes the screen in the app
public class MainController{

  private Stage stage;

  private Map<String, Scene> scenes = new HashMap<String, Scene>();
  private Map<String, SceneController> sceneControllers = new HashMap<String, SceneController>();

  private Main main;

  public MainController(Stage stage){
    this.stage = stage;
  }

  public void setMain(Main main) {
    this.main = main;
  }

  public Main getMain() {
    return main;
  }

  public Scene getScene(String name) {
    return scenes.get(name);
  }

  //Vet ikke om vi heller burde bruke throws her
  public void loadScene(String name, String resource) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource(resource));
      Parent parent = loader.load();
      Scene scene = new Scene(parent);
      SceneController sceneController = ((SceneController) loader.getController());
      sceneController.setMainController(this);
      scenes.put(name, scene);
      sceneControllers.put(name, sceneController);
    } catch (Exception e) {
      e.printStackTrace();
    }
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