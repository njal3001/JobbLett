package jobblett.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jobblett.core.Main;
import jobblett.json.JSONSerialize;

public class App extends Application {

  @Override
  public void start(final Stage primaryStage) throws Exception {
    final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Login.fxml"));
    final Parent parent = fxmlLoader.load();
    primaryStage.setScene(new Scene(parent));
    primaryStage.show();
    primaryStage.setOnCloseRequest(event -> {
      AbstractController loader = fxmlLoader.getController();
      Main main = loader.getMain();
      new JSONSerialize(main, "main.json").exportJSON();
    }
    );
  }

  public static void main(final String[] args) {
    launch(args);
  }
}
