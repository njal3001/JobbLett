package jobblett.ui;

import jobblett.core.Group;
import jobblett.core.Main;
import jobblett.core.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public abstract class AbstractController {
    
    protected Main main;

    protected void setMain(Main main){
        this.main = main;
        update();
    }

    protected Group getActiveGroup() {
        return main.getActiveGroup();
    }

    protected User getLoggedIn() {
        return main.getLoggedIn();
    }

    protected Main getMain() {
        return main;
    }

    // Burde finnes en bedre måte enn å bruke node hver gang for å finne stage
    protected void changeScreen(FXMLLoader loader, Node button, Main main) throws IOException {
        Stage stage = (Stage) button.getScene().getWindow();
        Parent root = loader.load();
        AbstractController controller = (AbstractController) loader.getController();
        controller.setMain(main);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    // Used by subclasses that need to update the UI when the scene changes
    protected void update(){

    }
}
