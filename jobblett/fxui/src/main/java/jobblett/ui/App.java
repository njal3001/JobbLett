package jobblett.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jobblett.core.Main;

//Uses code is from: https://github.com/acaicedo/JFX-MultiScreen/tree/master/ScreensFramework/src/screensframework

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

  private MainController mainContainer;

  @Override
  public void start(Stage primaryStage) {
    primaryStage.setTitle("Jobblett");

    mainContainer = new MainController();

    // Her kan man laste inn main fra fil istedet
    mainContainer.setMain(new Main());

    mainContainer.loadScreen(LOGIN_ID, LOGIN_FILE);
    mainContainer.loadScreen(CREATE_USER_ID, CREATE_USER_FILE);
    mainContainer.loadScreen(USER_HOME_ID, USER_HOME_FILE);
    mainContainer.loadScreen(JOIN_GROUP_ID, JOIN_GROUP_FILE);
    mainContainer.loadScreen(CREATE_GROUP_ID, CREATE_GROUP_FILE);
    mainContainer.loadScreen(GROUP_HOME_ID, GROUP_HOME_FILE);
    mainContainer.loadScreen(SHIFT_VIEW_ID, SHIFT_VIEW_FILE);
    mainContainer.loadScreen(CREATE_SHIFT_ID, CREATE_SHIFT_FILE);

    mainContainer.setScreen(LOGIN_ID);
    Scene scene = new Scene(mainContainer);
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public MainController getMainContainer() {
    return mainContainer;
  }

  public static void main(final String[] args) {
    launch(args);
  }
}
