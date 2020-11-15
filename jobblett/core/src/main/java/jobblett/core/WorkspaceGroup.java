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
    if (!workspace.getUserList().contains(user)) {
      throw new IllegalArgumentException("User must be added to the user list first");
    }
    super.addUser(user);
  }

  //TODO: javadoc
  public void addUser(String username) {
    addUser(workspace.getUserList().get(username));
  }

  public Workspace getWorkspace() {
    return workspace;
  }
}