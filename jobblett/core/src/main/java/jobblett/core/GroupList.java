package jobblett.core;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * Handles all of the groups in the "database".
 */
public class GroupList implements Iterable<Group> {

    private Collection<Group> groups = new ArrayList<>();

    /**
     * Generates a unique groupID with 4 digits which is available.
     *
     * @return the unique groupID
     * @throws IllegalStateException if all IDs are already taken
     */
    private int generateGroupId() throws IllegalStateException {
        Collection<Integer> alreadyUsed = getGroupIds();
        if (alreadyUsed.size() >= 9000)
            throw new IllegalStateException("All Ids are taken");
        int groupID = 0;
        while ((groupID == 0) || (alreadyUsed.contains(groupID))) {
            groupID = ThreadLocalRandom.current().nextInt(1000, 10000);
        }
        return groupID;
    }

    /**
     * Creates a new group with the given groupName and an unique ID.
     * The group is automatically added to groupList.
     *
     * @param groupName the groupName of the new group
     * @return the newly created group.
     * @throws IllegalStateException if all IDs are already taken
     */
    public Group newGroup(String groupName) throws IllegalStateException{
        int groupId = generateGroupId();
        Group group = new Group(groupName,groupId);
        addGroup(group);
        return group;
    }

    /**
     * Adds the group to the groupList.
     *
     * @param group the group to be added
     * @throws IllegalArgumentException if groupID is already taken
     */
    public boolean addGroup(Group group) throws IllegalArgumentException{
        if(getGroupIds().contains(group.getGroupID()))
            throw new IllegalArgumentException("Group ID is already taken");
        return groups.add(group);
    }

    /**
     * Removes the group from the groupList.
     *
     * @param group the group to be removed
     * @return true if the group is contained and removed, else false
     */
    public boolean removeGroup(Group group) {
        return groups.remove(group);
    }

    /**
     * Searches through the users with the same groupID and returns the first found.
     *
     * @param groupID the groupID to look for
     * @return the group with same groupID and returns null if there is none.
     */
    public Group getGroup(int groupID) {
        return groups.stream()
                .filter(group -> group.getGroupID() == groupID)
                .findFirst()
                .orElse(null);
    }

    /**
     * Gets the all groups where the specified user is a member.
     * Returns empty collection if there are none.
     *
     * @param user the specified user
     * @return a collection with groups
     */
    //LagTest
    public Collection<Group> getGroups(User user){
        return groups.stream()
                .filter(group -> group.getUser(user.getUserName())==user)
                .collect(Collectors.toList());
    }

    /**
     * Gets the amount of groups registered.
     *
     * @return amount of groups registered
     */
    public int getGroupSize() {
        return groups.size();
    }

    /**
     * Gets a list of all groupIDs registered.
     *
     * @return list of groupIDs (String)
     */
    private Collection<Integer> getGroupIds(){
        return groups.stream()
                .map(Group::getGroupID)
                .collect(Collectors.toList());
    }

    @Override
    public Iterator<Group> iterator() {
        return groups.iterator();
    }

    public String toString(){
        StringBuilder s = new StringBuilder();
        for(Group group : groups)
            s.append(group.toString()).append("\n");
        return s.toString();
    }


}