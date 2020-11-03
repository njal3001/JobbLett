package jobblett.ui;

import javafx.scene.control.ListCell;
import javafx.scene.text.Font;
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
      setFont(Font.loadFont(ButtonAnimationSkin.class.getResourceAsStream(App.BOLD_FONT_FILE),16));
    }
    else{
      setText(user.toString());
    }
  }
}