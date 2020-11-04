package jobblett.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public enum JobblettScenes {
    LOGIN_ID("Login.fxml"),
    CREATE_USER_ID("CreateUser.fxml"),
    USER_HOME_ID("UserHome.fxml"),
    JOIN_GROUP_ID("JoinGroup.fxml"),
    CREATE_GROUP_ID("CreateGroup.fxml"),
    GROUP_HOME_ID("GroupHome.fxml"),
    SHIFT_VIEW_ID("ShiftView.fxml"),
    UPDATE_SHIFT_ID("UpdateShift.fxml");

    private final String filename;
    private SceneController controller;
    private Scene scene;

    JobblettScenes(String filename) {
        this.filename = filename;
        reset();
    }

    public void reset() {
        FXMLLoader loader = new FXMLLoader(App.class.getResource(filename));
        Parent parent = null;
        try {
            parent = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(parent);
        SceneController sceneController = ((SceneController) loader.getController());
        sceneController.styleIt();
        this.controller = sceneController;
        this.scene = scene;
    }

    public String getFilename() {
        return filename;
    }

    public SceneController getController() {
        return controller;
    }

    public Scene getScene() {
        return scene;
    }

}
