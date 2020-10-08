package jobblett.ui;

import org.testfx.framework.junit5.ApplicationTest;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jobblett.core.Main;

public class AppTest extends ApplicationTest {
  private AbstractController controller;
  private Main main;

  public void start(final Stage primaryStage) throws Exception {
    //muligens en testFXMLfil
    final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Login.fxml"));
    final Parent parent = fxmlLoader.load();
    this.controller=fxmlLoader.getController();
    //fiks en getMain i abstraktkontroller
    this.main=this.controller.main;
    primaryStage.setScene(new Scene(parent));
    primaryStage.show();
  }

}