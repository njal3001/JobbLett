package jobblett.ui;

import java.beans.PropertyChangeEvent;
import java.util.Collection;
import jobblett.core.Group;
import jobblett.core.GroupList;
import jobblett.core.HashedPassword;
import jobblett.core.User;
import jobblett.core.UserList;
import jobblett.json.JobblettDeserializer;
import jobblett.json.JobblettSerializer;

public class JobblettDirectAccess implements JobblettAccess {

  private UserList userList;
  private GroupList groupList;

  /**
   * TODO.
   */
  public JobblettDirectAccess() {
    setUserList(new JobblettDeserializer<>(UserList.class).deserialize());
    setGroupList(new JobblettDeserializer<>(GroupList.class).deserialize());
    userList.addListener(this);
    groupList.addListener(this);
  }

  private void save() {
    new JobblettSerializer().writeValueOnDefaultLocation(groupList);
    new JobblettSerializer().writeValueOnDefaultLocation(userList);
  }

  private void setUserList(UserList userList) {
    this.userList = userList;
  }

  private void setGroupList(GroupList oldGroupList) {
    groupList = correctGroupList(oldGroupList, userList);
    ;
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

  @Override public User login(String userName, HashedPassword password) {
    return userList.checkUserNameAndPassword(userName, password);
  }

  @Override public Collection<Group> getGroups(User user) {
    return groupList.getGroups(user);
  }

  @Override public void propertyChange(PropertyChangeEvent evt) {
    save();
  }
}
