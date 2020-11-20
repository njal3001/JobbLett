package jobblett.core;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class WorkspaceGroupList extends GroupList {

  private final Workspace workspace;

  public WorkspaceGroupList(Workspace workspace) {
    this.workspace = workspace;
  }

  /**
   * Creates a new WorkspaceGroup with the given name
   * and a random group ID.
   *
   * @param groupName name of the new group
   * @return the group that was created
   *
   */
  @Override
  public Group newGroup(String groupName) {
    int id = super.generateGroupId();
    Group group = new WorkspaceGroup(groupName, id, workspace);
    super.add(group);
    return group;
  }


  /**
   * Adds the groups
   * If the group is not an instance of WorkspaceGroup
   * or does not have the same Workspace, its contents
   * are copied into a new WorkspaceGroup. 
   *
   * @param groups Groups to be added
   */
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
          if (group.isAdmin(user)) {
            workspaceGroup.addAdmin(workspaceGroup.getUser(user.getUsername()));
          }
        }
        for (JobShift jobShift : group.getJobShiftList()) {
          String jobShiftUserName = jobShift.getUser().getUsername();
          User realUser = workspaceGroup.getUser(jobShiftUserName);
          jobShift.setUser(realUser);
          workspaceGroup.addJobShift(jobShift, workspaceGroup.getAdmins().iterator().next());
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

  @Override public void addListener(PropertyChangeListener pcl) {
    super.addListener(pcl);
    forEach(group -> group.addListener(pcl));
  }
}
