package jobblett.core;

import com.fasterxml.jackson.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Represents a group in jobblett.
 */
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property = "groupID")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class Group implements Iterable<User> {

    private String groupName;
    private Collection<User> groupMembers = new ArrayList<>();
    private final int groupID;

    /**
     * Initialize a instance of Group with a groupName and groupID.
     *
     * @param groupName the groupName
     * @param groupID the groupID
     */
    @JsonCreator
    public Group(@JsonProperty("groupName") String groupName, @JsonProperty("groupID") int groupID) {
        setGroupName(groupName);
        this.groupID = groupID;
    }

    /**
     * Adds a user to the group.
     * An exception thrown if the user is already a part of the group.
     *
     * @param user the user to be added
     * @throws IllegalArgumentException if the user is already a groupMember
     */
    public void addUser(User user) throws IllegalArgumentException {
        checkExistingUser(user);
        this.groupMembers.add(user);
    }

    /**
     * Removes the user from groupMember-list.
     *
     * @param user the user that should be removed
     * @return returns true if the user was contained and removed, else false
     */
    public boolean removeUser(User user) {
        return this.groupMembers.remove(user);
    }

    /**
     * Searches through users with the same username and returns the first found.
     *
     * @param username the username used to search
     * @return  returns the user if found, else null.
     */
    public User getUser(String username) {
        return groupMembers.stream()
                .filter(group -> group.getUserName().equals(username))
                .findFirst()
                .orElse(null);
    }

    /**
     * Checks if a user with the same username already exist.
     *
     * @param user the user that should be checked
     * @throws IllegalArgumentException throws exception if the user already exist.
     */
    private void checkExistingUser(User user) throws IllegalArgumentException {
        if (this.groupMembers.contains(user)) {
            throw new IllegalArgumentException("This user is already in the group");
        }
    }

    /**
     * Checks if the new groupName is valid and changes the groupName.
     *
     * @param groupName the new groupName
     * @throws IllegalArgumentException invalid groupName throws exception
     */
    public void setGroupName(String groupName) throws IllegalArgumentException {
        checkGroupName(groupName);
        this.groupName = groupName;
    }


    /**
     * Checks if the groupName is more than 2 characters.
     *
     * @param groupName the groupName
     * @throws IllegalArgumentException throws exception if criteria is not fulfilled
     */
    private void checkGroupName(String groupName) throws IllegalArgumentException {
        if (groupName.trim().length() < 2) {
            throw new IllegalArgumentException("Group name must have at least 2 characters");
        }
    }

    /**
     * Gets the amount of members in a group.
     *
     * @return amount of members
     */
    @JsonIgnore
    public int getGroupSize() {
        return this.groupMembers.size();
    }

    /**
     * Gets the groupName.
     * The name is NOT unique. Use groupID for that purpose.
     *
     * @return the groupName
     */
    public String getGroupName() {
        return this.groupName;
    }

    /**
     * Gets the groupID.
     * This is unique identifier for the group.
     *
     * @return the groupID
     */
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
