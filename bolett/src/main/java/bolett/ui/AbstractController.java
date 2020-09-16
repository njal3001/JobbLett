package bolett.ui;

import bolett.core.Group;
import bolett.core.Main;
import bolett.core.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.EventListener;
import java.util.ResourceBundle;

public abstract class AbstractController {
    protected Main main = new Main();
    protected User activeUser = main.getLoggedIn();
    protected Group activeGroup = main.getActiveGroup();

    protected void changeScreen(String URLName, Node button) throws IOException {
        Stage stage = (Stage) button.getScene().getWindow();
        Parent root=FXMLLoader.load(getClass().getResource(URLName));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
