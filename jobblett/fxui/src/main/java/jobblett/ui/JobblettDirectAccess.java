package jobblett.ui;

import java.beans.PropertyChangeEvent;
import java.util.Collection;
import jobblett.core.Group;
import jobblett.core.GroupList;
import jobblett.core.HashedPassword;
import jobblett.core.User;
import jobblett.core.UserList;
import jobblett.json.JobblettPersistence;

public class JobblettDirectAccess implements JobblettAccess {

  private UserList userList;
  private GroupList groupList;

  /**
   * TODO.
   */
  public JobblettDirectAccess() {
    setUserList(new JobblettPersistence().readValue(UserList.class));
    setGroupList(new JobblettPersistence().readValue(GroupList.class));
    userList.addListener(this);
    groupList.addListener(this);
  }

  private void save() {
    new JobblettPersistence().writeValueOnDefaultLocation(groupList);
    new JobblettPersistence().writeValueOnDefaultLocation(userList);
  }

  private void setUserList(UserList userList) {
    this.userList = userList;
  }

  private void setGroupList(GroupList oldGroupList) {
    groupList = correctGroupList(oldGroupList, userList);
  }

  @Override public void setLists(UserList userList, GroupList groupList) {
    setUserList(userList);
    setGroupList(groupList);
  }

  @Override public Group newGroup(String groupName) {
    return groupList.newGroup(groupName);
  }

  @Override public void add(User newUser) {
    userList.add(newUser);
  }

  @Override public Group getGroup(int groupId) {
    return groupList.get(groupId);
  }

  @Override public User login(String userName, String password) {
    return userList.checkUserNameAndPassword(userName, new HashedPassword(password));
  }

  @Override public Collection<Group> getGroups(User user) {
    return groupList.getGroups(user);
  }

  @Override public void propertyChange(PropertyChangeEvent evt) {
    save();
  }
}
