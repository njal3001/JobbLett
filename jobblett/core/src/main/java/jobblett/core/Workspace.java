package jobblett.core;

public class Workspace {

  //Contains all users in the workspace
  private WorkspaceUserList userList;

  //Contains all groups in the workspace,
  //all group users must be in userList
  private WorkspaceGroupList groupList;

  public Workspace() {
    userList = new WorkspaceUserList(this);
    groupList = new WorkspaceGroupList(this);
  }

  public WorkspaceUserList getUserList() {
    return userList;
  }

  public WorkspaceGroupList getGroupList() {
    return groupList;
  }
}