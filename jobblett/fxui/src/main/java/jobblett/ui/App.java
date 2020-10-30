package jobblett.ui;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

import javafx.application.Application;
import javafx.stage.Stage;

//Code is inspired by: https://github.com/acaicedo/JFX-MultiScreen/tree/master/ScreensFramework/src/screensframework

public class App extends Application {

  public static final boolean REST_API_ON = false;

  public static final String LOGIN_ID = "Login";
  public static final String LOGIN_FILE = "Login.fxml";

  public static final String CREATE_USER_ID = "CreateUser";
  public static final String CREATE_USER_FILE = "CreateUser.fxml";

  public static final String USER_HOME_ID = "UserHome";
  public static final String USER_HOME_FILE = "UserHome.fxml";

  public static final String JOIN_GROUP_ID = "JoinGroup";
  public static final String JOIN_GROUP_FILE = "JoinGroup.fxml";

  public static final String CREATE_GROUP_ID = "CreateGroup";
  public static final String CREATE_GROUP_FILE = "CreateGroup.fxml";

  public static final String GROUP_HOME_ID = "GroupHome";
  public static final String GROUP_HOME_FILE = "GroupHome.fxml";

  public static final String SHIFT_VIEW_ID = "ShiftView";
  public static final String SHIFT_VIEW_FILE = "ShiftView.fxml";

  public static final String UPDATE_SHIFT_ID = "UpdateShift";
  public static final String UPDATE_SHIFT_FILE = "UpdateShift.fxml";

  public final static DateTimeFormatter EXPECTED_TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");
  public final static DateTimeFormatter EXPECTED_DATE_FORMAT = DateTimeFormatter.ofPattern("YYYY-MM-dd");


  private MainController mainController;

  @Override
  public void start(Stage primaryStage) throws IOException {
    primaryStage.setTitle("Jobblett");

    mainController = new MainController(primaryStage);

    mainController.loadScene(LOGIN_ID, LOGIN_FILE);
    mainController.loadScene(CREATE_USER_ID, CREATE_USER_FILE);
    mainController.loadScene(USER_HOME_ID, USER_HOME_FILE);
    mainController.loadScene(JOIN_GROUP_ID, JOIN_GROUP_FILE);
    mainController.loadScene(CREATE_GROUP_ID, CREATE_GROUP_FILE);
    mainController.loadScene(GROUP_HOME_ID, GROUP_HOME_FILE);
    mainController.loadScene(SHIFT_VIEW_ID, SHIFT_VIEW_FILE);
    mainController.loadScene(UPDATE_SHIFT_ID, UPDATE_SHIFT_FILE);

    mainController.setScene(LOGIN_ID);
    primaryStage.show();
  }

  public static void main(final String[] args) {
    launch(args);
  }
}
