package jobblett.ui;

import javafx.scene.control.ListCell;
import jobblett.core.Group;

public class GroupListCell extends ListCell<Group>{

  private MainController mainController;

  public GroupListCell(MainController mainController){
    this.mainController = mainController;
  }
  
  @Override
  public void updateItem(Group group, boolean empty){
    super.updateItem(group, empty);
    if(empty || group == null){
      setGraphic(null);
      setText(null);
    }
    else{
      setText(group.getGroupName());
      setOnMouseClicked((event) -> {
        mainController.setActiveGroup(group);
        mainController.setScene(App.GROUP_HOME_ID);
      });
    
    }
  }
}