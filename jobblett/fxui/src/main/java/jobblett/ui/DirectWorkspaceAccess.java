package jobblett.ui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import jobblett.core.Group;
import jobblett.core.HashedPassword;
import jobblett.core.JobShift;
import jobblett.core.User;
import jobblett.core.Workspace;
import jobblett.json.JobblettPersistence;


public class DirectWorkspaceAccess implements WorkspaceAccess, PropertyChangeListener {

  private Workspace workspace;

  public DirectWorkspaceAccess(Workspace workspace) {
    this.workspace = workspace;
    this.workspace.addListener(this);
  }

  private void save() {
    new JobblettPersistence().writeValueOnDefaultLocation(workspace);
  }

  /**
   * Creates a new group with the given name.
   *
   * @param groupName the name of the new group
   * @return Id of the newly created group
   */
  @Override public int newGroup(String groupName) {
    return workspace.getGroupList().newGroup(groupName).getGroupId();
  }

  private User getUser(String username) {
    checkUsername(username);
    return workspace.getUserList().get(username);
  }

  private void checkUsername(String username) {
    if (!hasUser(username)) {
      throw new IllegalArgumentException("No user with the username: " + username);
    }
  }

  /**
   * Adds a new User with the given parameters.
   *
   * @param username of the user
   * @param password of the user
   * @param givenName of the user
   * @param familyName of the user
   */
  @Override public void addUser(
      String username,
      String password,
      String givenName,
      String familyName) {
    User newUser = new User(username, new HashedPassword(password), givenName, familyName);
    workspace.getUserList().add(newUser);
  }

  /**
   * Checks if an user with the given username exists.
   *
   * @param username of a user
   * @return true if user with username exists, false otherwise
   */
  @Override public boolean hasUser(String username) {
    return workspace.getUserList().get(username) != null;
  }

  /**
   * Checks if there is an user with the given username and checks if password
   * matches.
   *
   * @param username username of the user
   * @param password password to be checked
   * @return true if password of user with given username matches the given password
   */
  @Override public boolean correctPassword(String username, String password) {
    if (!hasUser(username)) {
      return false;
    }
    return getUser(username).getPassword().matches(new HashedPassword(password));
  }

  /**
   * Gets the full name of the user with the given username.
   *
   * @param username of the user
   * @return the full name of the user
   */
  @Override public String getUserFullName(String username) {
    User user = getUser(username);
    return user.getGivenName() + " " + user.getFamilyName();
  }

  /**
   * Gets the toString of the user with the given username.
   *
   * @param username of the user
   * @return toString of the user
   */
  @Override public String getUserToString(String username) {
    return getUser(username).toString();
  }

  private void checkGroupId(int groupId) {
    if (!hasGroup(groupId)) {
      throw new IllegalArgumentException("No group with the ID: " + groupId);
    }
  }

  private Group getGroup(int groupId) {
    checkGroupId(groupId);
    return workspace.getGroupList().get(groupId);
  }

  /**
   * Checks if there exists a group with the given id.
   *
   * @param groupId of a group
   * @return true if group with given id exists, false otherwise
   */
  @Override public boolean hasGroup(int groupId) {
    return workspace.getGroupList().get(groupId) != null;
  }

  /**
   * Gets the group name of the group with the given id.
   *
   * @param groupId of the group
   * @return the name of the group
   */
  @Override public String getGroupName(int groupId) {
    return getGroup(groupId).getGroupName();
  }

  /**
   * Gets a collection of all group ids of groups which contain
   * the user with the given username.
   *
   * @param username of the user
   * @return a collection of group ids
   * 
   */
  @Override public Collection<Integer> getAllGroupIds(String username) {
    Collection<Integer> allIds = new ArrayList<>();
    workspace.getGroupList().getGroups(getUser(username))
        .forEach((group) -> allIds.add(group.getGroupId()));
    return allIds;
  }

  /**
   * Gets a collection of all usernames of users in the group
   * with the given id.
   *
   * @param groupId of the group
   * @return a collection of usernames
   */
  @Override public Collection<String> getGroupUsernames(int groupId) {
    Collection<String> allUsernames = new ArrayList<>();
    getGroup(groupId).forEach((user) -> allUsernames.add(user.getUsername()));
    return allUsernames;
  }

  /**
   * Adds the user with the given username to the group
   * with the given id.
   *
   * @param groupId of the group
   * @param username of the user
   */
  @Override public void addGroupMember(int groupId, String username) {
    getGroup(groupId).addUser(getUser(username));
  }

  /**
   * Checks if the group with given groupId contains the user with
   * the given username.
   *
   * @param groupId of the group
   * @param username of the user
   * @return true if the group contains the user, false otherwise
   */
  @Override public boolean hasGroupUser(int groupId, String username) {
    return getGroup(groupId).getUser(username) != null;
  }

  /**
   * Adds the user with the given username as an admin in the group
   * with the given groupId.
   *
   * @param groupId of the group
   * @param username of the user
   */
  @Override public void addGroupAdmin(int groupId, String username) {
    getGroup(groupId).addAdmin(getUser(username));
  }

  /**
   * Checks if the user with the given username is an admin of the 
   * group with the given groupId.
   *
   * @param groupId of the group
   * @param username of the user
   * @return true if user is admin in the group, false otherwise
   */
  @Override public boolean isGroupAdmin(int groupId, String username) {
    return getGroup(groupId).isAdmin(getUser(username));
  }

  private JobShift getJobShift(int groupId, int index) {
    return getGroup(groupId).getJobShiftList().get(index);
  }

  /**
   * An user with the given username deletes the jobshift with the given index
   * in the group with the given groupId.
   *
   * @param adminUsername username of the user
   * @param groupId of the group
   * @param index of the jobshift
   * @throws IllegalArgumentException if the user is not an admin in the group
   */
  @Override public void deleteJobShift(String adminUsername, int groupId, int index) {
    JobShift jobShift = getJobShift(groupId, index);
    User admin = getUser(adminUsername);
    getGroup(groupId).removeJobShift(admin, jobShift);
  }

  /**
   * An user with the given username adds a jobshift with the given values
   * in the group with the given groupId. 
   * Throws an exception if the user that adds the shift
   * is not an admin in the group or the jobshift user is not a member
   * of the group.
   *
   * @param adminUsername username of the user that adds the shift
   * @param groupId of the group
   * @param jobShiftUsername username of the user with the jobshift
   * @param startingTime of the jobshift
   * @param duration of the jobshift
   * @param info of the jobshift
   * @throws IllegalArgumentException if users are not valid
   */
  @Override
  public void addJobShift(String adminUsername, int groupId, String jobShiftUsername,
      LocalDateTime startingTime, Duration duration, String info) {
    JobShift jobShift = new JobShift(getUser(jobShiftUsername), startingTime, duration, info);
    getGroup(groupId).addJobShift(jobShift, getUser(adminUsername));
  }

  /**
   * Gets the number of jobshifts for the group with
   * the given groupId.
   */
  @Override public int getJobShiftsSize(int groupId) {
    return getGroup(groupId).getJobShiftList().size();
  }

  /** Gets a list of the indexes of the jobshifts in the group with the given groupId
   * filtered by the user with the given username.
   *
   * @param groupId of the group
   * @param username of the user
   * @return a filtered list of the jobshift indexes
   */
  @Override public List<Integer> getJobShiftIndexes(int groupId, String username) {
    List<Integer> indexes = new ArrayList<>();
    List<JobShift> jobShifts = getGroup(groupId).getJobShiftList()
        .getJobShifts(getUser(username));
    jobShifts.stream().forEach((jobShift) -> indexes.add(jobShifts.indexOf(jobShift)));
    return indexes;
  }

  /**
   * Gets the username of the user for the jobshift with the given index in
   * the group with the given groupId.
   *
   * @param groupId of the group
   * @param index of the jobshift
   * @return the username of jobshifts user
   */
  @Override public String getJobShiftUsername(int groupId, int index) {
    return getJobShift(groupId, index).getUser().getUsername();
  }

  /**
   * Gets the starting time of the jobshift with the given index in
   * the group with the given groupId.
   *
   * @param groupId of the group
   * @param index of the jobshift
   * @return the starting time of the jobshift
   */
  @Override public LocalDateTime getJobShiftStartingTime(int groupId, int index) {
    return getJobShift(groupId, index).getStartingTime();
  }

  /**
   * Gets the ending time of the jobshift with the given index in
   * the group with the given groupId.
   *
   * @param groupId of the group
   * @param index of the jobshift
   * @return the ending time of the jobshift
   */
  @Override public LocalDateTime getJobShiftEndingTime(int groupId, int index) {
    return getJobShift(groupId, index).getEndingTime();
  }

  /**
   * Gets the info of the jobshift with the given index in
   * the group with the given groupId.
   *
   * @param groupId of the group
   * @param index of the jobshift
   * @return the info of the jobshift
   */
  @Override public String getJobShiftInfo(int groupId, int index) {
    return getJobShift(groupId, index).getInfo();
  }

  /**
   * Checks if the jobshift with the given index in the group with the given
   * groupId, is outdated.
   *
   * @param groupId of the group
   * @param index of the jobshift
   * @return true if the jobshift is outdated, false otherwise
   */
  @Override public boolean jobShiftIsOutdated(int groupId, int index) {
    return getJobShift(groupId, index).isOutDated();
  }

  /**
   * Deletes alle outdated jobshifts in the group with the 
   * given groupId.
   *
   * @param groupId of the group
   */
  @Override public void deleteOutdatedJobShift(int groupId) {
    getGroup(groupId).deleteOutdatedJobShifts();
  }

  /**
   * Autosaves when a property is changed.
   */
  @Override public void propertyChange(PropertyChangeEvent evt) {
    save();
  }
}
