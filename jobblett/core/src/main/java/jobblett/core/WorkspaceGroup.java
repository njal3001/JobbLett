package jobblett.core;

public class WorkspaceGroup extends Group {

  private final Workspace workspace;

  public WorkspaceGroup(String groupName, int groupId, Workspace workspace) {
    super(groupName, groupId);
    this.workspace = workspace;
  }

  /**
   * Adds user to the spesificed group.
   * User must be in the user list
   *
   * @param user User to be added
  */
  @Override
  public void addUser(User user) {
    addUser(user.getUsername());
  }

  /**
   * Finds the user with the given username in the 
   * user list and adds it to the group.
   *
   * @param username Username of the user to be added
   */
  public void addUser(String username) {
    User user = workspace.getUserList().get(username);
    if (user == null) {
      throw new IllegalArgumentException("User must be added to the user list first");
    }
    super.addUser(user);
  }

  public Workspace getWorkspace() {
    return workspace;
  }
}
