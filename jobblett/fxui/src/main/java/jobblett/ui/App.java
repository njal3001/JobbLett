package jobblett.ui;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.format.DateTimeFormatter;

import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import jobblett.core.Group;
import jobblett.core.User;

import static jobblett.ui.JobblettScenes.*;
import static jobblett.ui.SceneController.switchScene;

//Code is inspired by: https://github.com/acaicedo/JFX-MultiScreen/tree/master/ScreensFramework/src/screensframework

public class App extends Application {

  public static final boolean REST_API_ON = false;

  public final static DateTimeFormatter EXPECTED_TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");
  public final static DateTimeFormatter EXPECTED_DATE_FORMAT = DateTimeFormatter.ofPattern("YYYY-MM-dd");

  public static final String FONT_FILE = "Product-Sans-Regular.ttf";
  public static final String BOLD_FONT_FILE = "Product-Sans-Bold.ttf";

  @Override
  public void start(Stage primaryStage) throws IOException {
    Font.loadFont(ButtonAnimationSkin.class.getResourceAsStream(App.FONT_FILE),16);
    Font.loadFont(ButtonAnimationSkin.class.getResourceAsStream(App.BOLD_FONT_FILE),16);
    primaryStage.setTitle("Jobblett");


    loadScenes(primaryStage);

    switchScene(LOGIN_ID);
    primaryStage.show();
  }

  public static void loadScenes(Stage stage) throws IOException {
    for (JobblettScenes jobblettScenes : JobblettScenes.values()){
      jobblettScenes.reset();
    }
    SceneController.setStage(stage);
    if (App.REST_API_ON) {
      try {
        SceneController.setAccess( new JobblettRemoteAccess(new URI("")));// Has to be updated to the right URI
      } catch (URISyntaxException e) {
        e.printStackTrace();
      }
    }
    else SceneController.setAccess(new JobblettDirectAccess());
  }

  public static void main(final String[] args) {
    launch(args);
  }
}
