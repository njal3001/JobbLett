package jobblett.ui;

import javafx.scene.control.ListCell;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import jobblett.core.Group;
import jobblett.core.User;

public class GroupMemberListCell extends ListCell<User>{

  private MainController mainController;

  public GroupMemberListCell(MainController mainController){
    this.mainController = mainController;
  }
  
  @Override
  public void updateItem(User user, boolean empty){
    super.updateItem(user, empty);
    if(empty || user == null){
      setGraphic(null);
      setText(null);
    }
    else if(mainController.getActiveGroup().isAdmin(user)){
      setText(user.toString() + " [Admin]");
      setFont(Font.font("Arial", FontWeight.BOLD,12));
    }
    else{
      setText(user.toString());
    }
  }
}