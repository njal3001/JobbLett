package jobblett.ui;

import static jobblett.ui.JobblettScenes.LOGIN;
import static jobblett.ui.SceneController.switchScene;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.format.DateTimeFormatter;
import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.stage.Stage;

//Code is inspired by: https://github.com/acaicedo/JFX-MultiScreen/tree/master/ScreensFramework/src/screensframework


public class App extends Application {

  public static final boolean REST_API_ON = false;

  public static final DateTimeFormatter EXPECTED_TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");
  public static final DateTimeFormatter EXPECTED_DATE_FORMAT =
      DateTimeFormatter.ofPattern("YYYY-MM-dd");

  public static final String FONT_FILE = "Product-Sans-Regular.ttf";
  public static final String BOLD_FONT_FILE = "Product-Sans-Bold.ttf";

  /**
   * TODO.
   *
   * @param stage TODO
   * @throws IOException TODO
   */
  public static void loadScenes(Stage stage) {
    for (JobblettScenes jobblettScenes : JobblettScenes.values()) {
      jobblettScenes.reset();
    }
    SceneController.setStage(stage);
    if (App.REST_API_ON) {
      try {
        // Has to be updated to the right URI
        SceneController.setAccess(new JobblettRemoteAccess(new URI("")));
      } catch (URISyntaxException e) {
        e.printStackTrace();
      }
    } else {
      SceneController.setAccess(new JobblettDirectAccess());
    }
  }

  public static void main(final String[] args) {
    launch(args);
  }

  @Override public void start(Stage primaryStage) throws IOException {
    commonStart(primaryStage);
    switchScene(LOGIN);
    primaryStage.show();
  }

  /**
   * A common methods used in the startmethod for the app and the tests.
   *
   * @param primaryStage primaryStage from main start method
   */
  public static void commonStart(Stage primaryStage) {
    Font.loadFont(ButtonAnimationSkin.class.getResourceAsStream(App.FONT_FILE), 16);
    Font.loadFont(ButtonAnimationSkin.class.getResourceAsStream(App.BOLD_FONT_FILE), 16);
    primaryStage.setTitle("Jobblett");
    loadScenes(primaryStage);
  }
}
