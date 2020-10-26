package jobblett.ui;

import jobblett.core.*;
import jobblett.json.JobblettDeserializer;
import jobblett.json.JobblettLocalSerializer;

import java.util.Collection;

public class JobblettDirectAccess implements JobblettAccess{

    private UserList userList;
    private GroupList groupList;

    public JobblettDirectAccess() {
        setUserList(new JobblettDeserializer<UserList>(UserList.class).deserialize());
        setGroupList(new JobblettDeserializer<GroupList>(GroupList.class).deserialize());
    }

    @Override
    public void save() {
        new JobblettLocalSerializer(groupList).exportJSON();
        new JobblettLocalSerializer(userList).exportJSON();
    }

    @Override
    public void setUserList(UserList userList) {
        this.userList = userList;
    }

    @Override
    public void setGroupList(GroupList oldGroupList) {
        groupList = correctGroupList(oldGroupList,userList);;
    }

    @Override
    public Group newGroup(String groupName) {
        return groupList.newGroup(groupName);
    }

    @Override
    public void addUser(User newUser) {
        userList.addUser(newUser);
    }

    @Override
    public Group getGroup(int groupID){
        return groupList.getGroup(groupID);
    }

    @Override
    public User login(String userName, String password) {
        return userList.login(userName,password);
    }

    @Override
    public Collection<Group> getGroups(User user) {
        return groupList.getGroups(user);
    }
}
