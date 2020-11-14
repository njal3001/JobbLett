package jobblett.core;

public class WorkspaceUserList extends UserList {

  private final Workspace workspace;

  public WorkspaceUserList(Workspace workspace) {
    this.workspace = workspace;
  }

  /**
   * Removes user.
   * If user is in any groups, it is also
   * removed from the group
   *
   * @param user User to be removed
  */
  public void removeUser(User user) {
    super.remove(user);
    for (Group group : workspace.getGroupList()) {
      if (group.getUser(user.getUsername()) != null) {
        group.removeUser(user);
      }
    }
  }
}