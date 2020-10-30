package jobblett.core;

import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * Handles all of the groups in the "database".
 */
public class GroupList extends JobblettList<Integer, Group> {
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
        add(group);
        return group;
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
        return filter(group -> group.getUser(user.getUserName())==user);
    }

    /**
     * Gets a list of all groupIDs registered.
     *
     * @return list of groupIDs (String)
     */
    private Collection<Integer> getGroupIds(){
        return stream()
                .map(Group::getGroupID)
                .collect(Collectors.toList());
    }

    @Override
    protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        super.firePropertyChange(propertyName, oldValue, newValue);
    }

    @Override
    protected Integer identifier(Group type) {
        return type.getGroupID();
    }

    @Override
    protected void optionalAlreadyExists() {
        throw new IllegalArgumentException("Group with same ID already exists");
    }

}