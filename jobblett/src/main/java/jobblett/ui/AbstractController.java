package jobblett.ui;

import jobblett.core.Group;
import jobblett.core.Main;
import jobblett.core.AbstractUser;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class AbstractController {
    protected Main main = new Main();
    protected AbstractUser activeUser = main.getLoggedIn();
    protected Group activeGroup = main.getActiveGroup();

    protected void changeScreen(String URLName, Node button) throws IOException {
        Stage stage = (Stage) button.getScene().getWindow();
        Parent root=FXMLLoader.load(getClass().getResource(URLName));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
