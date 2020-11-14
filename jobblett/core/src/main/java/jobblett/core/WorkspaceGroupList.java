package jobblett.core;

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
}