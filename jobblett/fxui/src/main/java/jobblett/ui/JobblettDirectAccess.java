package jobblett.ui;

import jobblett.core.*;
import jobblett.json.JobblettDeserializer;
import jobblett.json.JobblettSerializer;

import java.beans.PropertyChangeEvent;
import java.util.Collection;

public class JobblettDirectAccess implements JobblettAccess{

    private UserList userList;
    private GroupList groupList;

    public JobblettDirectAccess() {
        setUserList(new JobblettDeserializer<UserList>(UserList.class).deserialize());
        setGroupList(new JobblettDeserializer<GroupList>(GroupList.class).deserialize());
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
        groupList = correctGroupList(oldGroupList,userList);;
    }

    @Override
    public void setLists(UserList userList, GroupList groupList) {
        setUserList(userList);
        setGroupList(groupList);
    }

    @Override
    public Group newGroup(String groupName) {
        return groupList.newGroup(groupName);
    }

    @Override
    public void add(User newUser) {
        userList.add(newUser);
    }

    @Override
    public Group getGroup(int groupID){
        return groupList.get(groupID);
    }

    @Override
    public User login(String userName, String password) {
        return userList.checkUserNameAndPassword(userName,password);
    }

    @Override
    public Collection<Group> getGroups(User user) {
        return groupList.getGroups(user);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        save();
    }
}
