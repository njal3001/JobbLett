package jobblett.ui;

import jobblett.core.Group;
import jobblett.core.Main;
import jobblett.core.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public abstract class AbstractController {
    protected Main main;
    protected User activeUser;
    protected Group activeGroup;

    protected AbstractController(){
        main = new Main();
    }

    protected void setMain(Main main){
        this.main = main;
        this.activeUser = main.getLoggedIn();
        this.activeGroup = main.getActiveGroup();
        update();
    }

    protected void changeScreen(String URLName, Node button, Main main) throws IOException {
        Stage stage = (Stage) button.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(URLName));
        Parent root = loader.load();
        AbstractController controller = (AbstractController) loader.getController();
        controller.setMain(main);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    protected void update(){

    }
}
