package jobblett.ui;

import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;

import jobblett.core.GroupList;
import jobblett.core.UserList;
import jobblett.json.JSONDeserialize;
import jobblett.json.JSONSerialize;

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

  public static final String UPDATE_SHIFT_ID = "UpdateShift";
  public static final String UPDATE_SHIFT_FILE = "UpdateShift.fxml";

  private MainController mainController;

  @Override
  public void start(Stage primaryStage) throws IOException {
    primaryStage.setTitle("Jobblett");

    mainController = new MainController(primaryStage);

    UserList userList = new JSONDeserialize<UserList>(UserList.class).importJSON();
    GroupList groupList = new JSONDeserialize<GroupList>(GroupList.class).importJSON();
    mainController.setUserList(userList);
    mainController.setGroupList(groupList);

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

    primaryStage.setOnCloseRequest(event -> {
      new JSONSerialize(groupList).exportJSON();
      new JSONSerialize(userList).exportJSON();
    });
  }

  public static void main(final String[] args) {
    launch(args);
  }
}
