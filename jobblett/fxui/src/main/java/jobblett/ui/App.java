package jobblett.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jobblett.core.Main;

//Code is inspired by: https://github.com/acaicedo/JFX-MultiScreen/tree/master/ScreensFramework/src/screensframework

public class App extends Application {

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

  public static final String CREATE_SHIFT_ID = "CreateShift";
  public static final String CREATE_SHIFT_FILE = "CreateShift.fxml";

  private MainController mainController;

  @Override
  public void start(Stage primaryStage) {
    primaryStage.setTitle("Jobblett");

    mainController = new MainController(primaryStage);

    // Her kan man laste inn main fra fil istedet
    mainController.setMain(new Main());

    mainController.loadScene(LOGIN_ID, LOGIN_FILE);
    mainController.loadScene(CREATE_USER_ID, CREATE_USER_FILE);
    mainController.loadScene(USER_HOME_ID, USER_HOME_FILE);
    mainController.loadScene(JOIN_GROUP_ID, JOIN_GROUP_FILE);
    mainController.loadScene(CREATE_GROUP_ID, CREATE_GROUP_FILE);
    mainController.loadScene(GROUP_HOME_ID, GROUP_HOME_FILE);
    mainController.loadScene(SHIFT_VIEW_ID, SHIFT_VIEW_FILE);
    mainController.loadScene(CREATE_SHIFT_ID, CREATE_SHIFT_FILE);

    mainController.setScene(LOGIN_ID);
    primaryStage.show();
  }

  public MainController getMainController() {
    return mainController;
  }

  public static void main(final String[] args) {
    launch(args);
  }
}
