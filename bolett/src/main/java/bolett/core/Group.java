package bolett.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

@JsonRootName(value = "Group")
public class Group implements Iterable<User> {

    private String groupName;
    private Collection<User> groupMembers = new ArrayList<>();
    private final int groupID;

    @JsonCreator
    public Group(@JsonProperty("groupName") String groupName, @JsonProperty("groupID") int groupID) {
        setGroupName(groupName);
        this.groupID = groupID;
    }

    public void addUser(User user) {
        checkExistingUser(user);
        this.groupMembers.add(user);
    }

    public void removeUser(User user) {
        this.groupMembers.remove(user);
    }

    public User getUser(String username) {
        return groupMembers.stream()
                .filter(group -> group.getUserName().equals(username))
                .findFirst()
                .orElse(null);
    }

    private void checkExistingUser(User user) {
        if (this.groupMembers.contains(user)) {
            throw new IllegalArgumentException("This user is already in the group");
        }
    }

    public void setGroupName(String groupName) {
        checkGroupName(groupName);
        this.groupName = groupName;
    }

    private void checkGroupName(String groupName) {
        if (groupName.trim().length() < 2) {
            throw new IllegalArgumentException("Group name must have at least 2 characters");
        }
    }

    @JsonIgnore
    public int getGroupSize() {
        return this.groupMembers.size();
    }

    public String getGroupName() {
        return this.groupName;
    }

    public int getGroupID() {
        return groupID;
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
