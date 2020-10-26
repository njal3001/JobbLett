package jobblett.ui;

import jobblett.core.*;

import java.util.ArrayList;
import java.util.Collection;

public interface JobblettAccess {

    public Group newGroup(String groupName);

    public void addUser(User newUser);

    public Group getGroup(int groupID);

    public User login(String userName, String password);

    public Collection<Group> getGroups(User user);

    public void save();

    public void setUserList(UserList userList);

    public void setGroupList(GroupList groupList);

    public default GroupList correctGroupList(GroupList oldGroupList, UserList userList) {
        GroupList groupList = new GroupList();
        for (Group group : oldGroupList) {
            // Remove duplicate users and replace with original users
            Collection<User> duplicateUsers = new ArrayList<>();
            for (User user : group) {
                if (userList.getUser(user.getUserName()) != null)
                    duplicateUsers.add(user);
                else
                    System.out.println("The user " + user.toString() + " is not contained in userList but contained in " + group.toString() + ".");
            }
            for (User user : duplicateUsers) {
                User originalUser = userList.getUser(user.getUserName());
                group.removeUser(user);
                group.addUser(originalUser);
            }

            // Remove duplicate users in JobShifts
            for (JobShift shift : group.getJobShifts()) {
                User user = shift.getUser();
                if (user == null) break;
                User originalUser = userList.getUser(user.getUserName());
                if (originalUser != null)
                    shift.setUser(originalUser);
                else
                    System.out.println("The user " + user.toString() + " is not contained in userList but contained in " + shift.toString() + ".");
            }
            groupList.addGroup(group);
        }
        return groupList;
    }

}
