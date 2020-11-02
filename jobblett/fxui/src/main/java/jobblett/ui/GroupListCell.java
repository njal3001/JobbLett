package jobblett.ui;

import javafx.scene.control.ListCell;
import jobblett.core.Group;

import static jobblett.ui.JobblettScenes.GROUP_HOME_ID;
import static jobblett.ui.SceneController.switchScene;

public class GroupListCell extends ListCell<Group>{

  
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
        SceneController.setActiveGroup(group);
        switchScene(GROUP_HOME_ID);
      });
    
    }
  }
}