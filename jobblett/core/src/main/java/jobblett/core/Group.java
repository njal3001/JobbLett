package jobblett.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Represents a group in jobblett.
 */
public class Group implements Iterable<User> {

    private String groupName;
    private Collection<User> groupMembers = new ArrayList<>();
    private final int groupID;
    private JobShiftList jobShifts = new JobShiftList();
    private User admin;


    // Føles ikke riktig å gjøre det slik, men tenker at vi må ha noe logikk som gjør at en vanlig user ikke kan lage job shift. 
    //Men dette vil ikke bli brukt i kontrolleren siden der fjerner vi bare knappen for å lage job shift hvis man ikke er admin
    //Burde finne en annen måte å gjøre det på kanskje
    public void addJobShift(JobShift jobShift, User user) {
        if(!isAdmin(user))
            throw new IllegalArgumentException("It's only admin that can add new job shift");
        jobShifts.addJobShift(jobShift);
    }

    /**
     * Initialize a instance of Group with a groupName and groupID.
     *
     * @param groupName the groupName
     * @param groupID the groupID
     */
    public Group(String groupName, int groupID) {
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
        // The first member to join the group becomes admin
        if(groupMembers.size() == 0 && admin == null)
            admin = user;
        this.groupMembers.add(user);
    }

    public boolean isAdmin(User user){
        return user == admin;
    }

    public User getAdmin(){
        return admin;
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
     * Checks if user is already a member of the group.
     *
     * @param user the user that should be checked
     * @throws IllegalArgumentException throws exception if the user already exist.
     */
    private void checkExistingUser(User user) throws IllegalArgumentException {
        if (this.groupMembers.contains(user)) {
          //Vet ikke om jeg skal ha det sånn, passer bedre for UIen, 
          //men er mer passende generelt å skrive "User is already a member of the group"
            throw new IllegalArgumentException("You are already a member of the group");
        }
    }

    /**
     * Checks if the new group name is valid and changes the groupName.
     *
     * @param groupName the new group name
     * @throws IllegalArgumentException invalid group name throws exception
     */
    public void setGroupName(String groupName) throws IllegalArgumentException {
        checkGroupName(groupName);
        this.groupName = groupName;
    }

    /**
     * Checks if the group name is more than 1 character.
     *
     * @param groupName the group name
     * @throws IllegalArgumentException throws exception if criteria is not fulfilled
     */
    private void checkGroupName(String groupName) throws IllegalArgumentException {
        if (groupName.trim().length() < 2) {
            throw new IllegalArgumentException("Group name must have at least 2 characters");
        }
    }

    /**
     * Gets the number of members in a group.
     *
     * @return amount of members
     */
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

    public JobShiftList getJobShifts() {
        return jobShifts;
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

    @Override
    public boolean equals(Object o) {
        if (o instanceof Group) {
            Group group = (Group) o;

            for (User thatUser : group) {
                if (getUser(thatUser.getUserName())==null) return false;
                User thisUser = getUser(thatUser.getUserName());
                if (!thisUser.equals(thatUser)) return false;
            }
            for (User thisUser : this) {
                if (group.getUser(thisUser.getUserName())==null) return false;
                User thatUser = group.getUser(thisUser.getUserName());
                if (!thatUser.equals(thisUser)) return false;
            }
            return true;
        }
        else return false;
    }

    @Override
    public int hashCode() {
        assert false : "hashCode not designed";
        return 42; // any arbitrary constant will do
    }
}
