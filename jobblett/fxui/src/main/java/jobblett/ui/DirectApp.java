package jobblett.ui;

import javafx.application.Application;
import javafx.stage.Stage;

public class DirectApp extends Application {

  @Override
  public void start(Stage stage) throws Exception {
    App app = new App(false);
    app.start(stage);
  }
}