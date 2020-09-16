package bolett.core;

import java.util.ArrayList;
import java.util.Collection;

public class Group {

    private String groupname;
    private Collection<User> groupmembers = new ArrayList<User>();
    private static int groupdID = 1;

    public Group(String groupname) {
        //  checkExistingGroupName(groupname);
        checkGroupName(groupname);
        this.groupname = groupname;
    }

    public Group(String groupname, int groupID) {
        //  checkExistingGroupName(groupname);
        this.groupname = groupname;
        setGroupID(groupID);

    }

    private void addUser(User user) {
        checkExistingUser(user);
        this.groupmembers.add(user);
    }

    private void removeUser(User user) {
        this.groupmembers.remove(user);
    }

    public void setGroupName(String groupName) {
        checkGroupName(groupName);
        this.groupName = groupName;
    }

    private void checkGroupName(String groupname) {
        if (groupname.length() < 2) {
            throw new IllegalArgumentException("Grouname length must be atleast 2 lettars");
        }
    }

    private void checkExistingUser(User user) {
        if (this.groupMembers.contains(user))
            throw new IllegalArgumentException("This user is already in the group");
    }

    @JsonIgnore
    public int getGroupSize() {
        return this.groupMembers.size();
    }

    public int getGroupSize() {
        return this.groupmembers.size();
    }

    public String getGroupname() {
        return this.groupname;
    }

    public User getUser(String username) {
        return groupMembers.stream().filter(user -> user.getUserName().equals(username)).findFirst().orElse(null);
    }

    @Override
    public String toString() {
        StringBuilder members = new StringBuilder();
        for (User user : this) {
            members.append(user.toString()).append(", ");
        }
        members.setLength(members.length() - 2);
        return this.groupName + ": " + members;
    }

    @Override
    public Iterator<User> iterator() {
        return groupMembers.iterator();
    }

}
