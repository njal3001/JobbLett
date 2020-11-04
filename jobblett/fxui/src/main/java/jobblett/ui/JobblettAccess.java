package jobblett.ui;

import jobblett.core.*;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collection;

public interface JobblettAccess extends PropertyChangeListener {

    public Group newGroup(String groupName);

    public void add(User newUser);

    public Group getGroup(int groupID);

    public User login(String userName, HashedPassword password);

    public Collection<Group> getGroups(User user);

    public void setLists(UserList userList, GroupList groupList);

    public default GroupList correctGroupList(GroupList oldGroupList, UserList userList) {
        GroupList groupList = new GroupList();
        for (Group group : oldGroupList) {
            // Remove duplicate users and replace with original users
            Collection<User> duplicateUsers = new ArrayList<>();
            for (User user : group) {
                if (userList.get(user.getUserName()) != null)
                    duplicateUsers.add(user);
                else
                    System.out.println("The user " + user.toString() + " is not contained in userList but contained in " + group.toString() + ".");
            }
            for (User user : duplicateUsers) {
                User originalUser = userList.get(user.getUserName());
                group.removeUser(user);
                group.addUser(originalUser);
            }

            Collection<User> duplicateAdmins = new ArrayList<>();
            for (User user : group.getAdmins()) {
                if (userList.get(user.getUserName()) != null)
                    duplicateAdmins.add(user);
                else
                    System.out.println("The user " + user.toString() + " is not contained in userList but contained in " + group.toString() + ".");
            }
            for (User user : duplicateAdmins) {
                User originalUser = userList.get(user.getUserName());
                group.removeAdmin(user);
                group.addAdmin(originalUser);
            }


            // Remove duplicate users in JobShifts
            for (JobShift shift : group.getJobShifts()) {
                User user = shift.getUser();
                if (user == null) break;
                User originalUser = userList.get(user.getUserName());
                if (originalUser != null)
                    shift.setUser(originalUser);
                else
                    System.out.println("The user " + user.toString() + " is not contained in userList but contained in " + shift.toString() + ".");
            }
            groupList.add(group);
        }
        return groupList;
    }

}
