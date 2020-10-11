package jobblett.ui;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jobblett.core.Group;
import jobblett.core.JobShift;
import jobblett.core.Main;
import jobblett.core.User;

public abstract class JobbLettTest extends ApplicationTest{

  protected Main main;
  protected User user1, user2;
  protected Group group1, group2;
  protected JobShift jobShift1, jobShift2;

  protected AbstractController controller;
  protected String fxmlFileName;

  //Alle subklasser må oppdatere fxmlFileName for at det ikke skal komme error
  @Override
  public void start(final Stage stage) throws Exception{
    FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFileName));
    Parent root = loader.load();
    controller = loader.getController();
    stage.setScene(new Scene(root));
    stage.show();
  }

  @BeforeEach
  public void initalize(){
    setupData();
    controller.setMain(main);
  }

  protected void setupData(){
    main = new Main();
    user1 = new User("CorrectUsername", "CorrectPassword12345", "Ole", "Dole");
    user2 = new User("CorrectUsername2", "CorrectPassword12345", "Hans", "Henrik");
    main.getUserList().addUser(user1);
    group1 = main.getGroupList().newGroup("Test Group 1");
    group2 = main.getGroupList().newGroup("Test Group 2");
    group1.addUser(user1);
    group1.addUser(user2);
  }

  @Test
  public void testController(){
    assertNotNull(controller);
  }
}