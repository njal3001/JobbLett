package bolett.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class GroupList implements Iterable<Group> {

    private Collection<Group> groups = new ArrayList<Group>();

    // Generates a unique groupID with 4 digits.
    private int generateGroupId() {
        Collection<Integer> alreadyUsed = getGroupIds();
        if (alreadyUsed.size() >= 9000)
            throw new IllegalStateException("All Ids are taken");
        int groupID = 0;
        while ((groupID == 0) || (alreadyUsed.contains(groupID))) {
            groupID = ThreadLocalRandom.current().nextInt(1000, 10000);
        }
        return groupID;
    }

    // Generates a new group with the given groupname and an unique ID
    public Group newGroup(String groupName) {
        int groupId = generateGroupId();
        Group group = new Group(groupName,groupId);
        addGroup(group);
        return group;
    }

    public void addGroup(Group group) {
        if(getGroupIds().contains(group.getGroupID()))
            throw new IllegalArgumentException("Group ID is already taken");
        groups.add(group);
    }

    public void removeGroup(Group group) {
        groups.remove(group);
    }

    public Group getGroup(int groupID) {
        return groups.stream().filter(group -> group.getGroupID() == groupID).findFirst().orElse(null);
    }

    public int getGroupSize() {
        return groups.size();
    }

    private Collection<Integer> getGroupIds(){
        return groups.stream().map(group -> group.getGroupID()).collect(Collectors.toList());
    }

    @Override
    public Iterator<Group> iterator() {
        return groups.iterator();
    }

    public String toString(){
        String s = "";
        for(Group group : groups)
            s += group.toString() + "\n";
        return s;
    }


}