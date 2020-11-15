package jobblett.core;

import java.util.ArrayList;

public class WorkspaceGroupList extends GroupList {

  private final Workspace workspace;

  public WorkspaceGroupList(Workspace workspace) {
    this.workspace = workspace;
  }

  @Override
  public Group newGroup(String groupName) {
    int id = super.generateGroupId();
    Group group = new WorkspaceGroup(groupName, id, workspace);
    super.add(group);
    return group;
  }


  // TODO:SKRIV JAVADOC, metoden er litt rar og må testes
  @Override
  public boolean add(Group... groups) {
    ArrayList<WorkspaceGroup> workspaceGroups = new ArrayList<>();
    for (Group group : groups) {
      WorkspaceGroup workspaceGroup;
      if (!(group instanceof WorkspaceGroup) 
          || ((WorkspaceGroup) group).getWorkspace() != this.workspace) {
        workspaceGroup = new WorkspaceGroup(group.getGroupName(), group.getGroupId(), workspace);
        for (User user : group) {
          workspaceGroup.addUser(user.getUsername());
        }
      } else {
        workspaceGroup = (WorkspaceGroup) group;
      }
      workspaceGroups.add(workspaceGroup);
    }
    return super.add(workspaceGroups.toArray(new WorkspaceGroup[workspaceGroups.size()]));
  }

  public Workspace getWorkspace() {
    return workspace;
  }
}
