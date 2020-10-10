package jobblett.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import jobblett.core.Group;
import jobblett.core.Main;
import jobblett.core.User;

public class GroupHomeTest extends ApplicationTest{

  private GroupHomeController controller;
  private Group group;
  private User user;
  private User user2;

  @Override
  public void start(final Stage stage) throws Exception{
    FXMLLoader loader = new FXMLLoader(getClass().getResource("GroupHome.fxml"));
    Parent root = loader.load();
    controller = loader.getController();
    stage.setScene(new Scene(root));
    stage.show();

    //Midlertidig
    controller.setMain(getMain());
  }

  //Midlertidig, initalisering med json testdata skal implementeres
  private Main getMain(){
    user = new User("CorrectUsername", "CorrectPassword12345", "Ole", "Dole");
    user2 = new User("CorrectUsername2", "CorrectPassword12345", "Hans", "Henrik");
    Main main = new Main();
    group = main.getGroupList().newGroup("Test Group");
    group.addUser(user);
    group.addUser(user2);
    main.getUserList().addUser(user);
    main.getUserList().addUser(user2);
    main.logIn(user);
    main.setActiveGroup(group);
    return main;
  }

  //Samme metode brukes i flere tester, lage superklasse for slike metoder?
  @Test
  public void testController(){
    assertNotNull(controller);
  }

  @Test
  public void testMembersShowingInView(){
    assertListViewHasItem(user.toString());
    assertListViewHasItem(user2.toString());
  }

  @Test
  public void testCorrectGroupId(){
    Text groupIdText = lookup("#groupID").query();
    assertEquals(String.valueOf(group.getGroupID()), groupIdText.getText());
  }

  //Samme metode brukes i flere testklasser, burde generaliseres
  private void assertListViewHasItem(String itemText){
    ListView<Text> members = lookup("#members").query();
    assertNotNull(members.getItems().stream().filter(text -> text.getText().equals(itemText)).findFirst().orElse(null));
  }
}