package bolett;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Main {
    private static Collection<User> users = new ArrayList<User>();
    private static Collection<Group> groups = new ArrayList<Group>();

    public Main() {

    }


    public User getUser(String username) {
        return users.stream()
                .filter(a -> a.getUserName().equals(username))
                .findAny()
                .orElse(null);
    }

    public Collection<User> searchUsers(String searchQuery) {
        Collection<User> matchedUsernames = users.stream()
                .filter(a -> a.getUserName().toLowerCase().contains(searchQuery.toLowerCase()))
                .collect(Collectors.toList());

        Collection<User> matchedGivenNames = users.stream()
                .filter(a -> a.getGivenName().toLowerCase().contains(searchQuery.toLowerCase()))
                .collect(Collectors.toList());

        Collection<User> matchedFamilyNames = users.stream()
                .filter(a -> a.getFamilyName().toLowerCase().contains(searchQuery.toLowerCase()))
                .collect(Collectors.toList());

        Collection<User> matchedUsers = new ArrayList<User>();
        matchedUsers.addAll(matchedFamilyNames);
        matchedUsers.addAll(matchedGivenNames);
        matchedUsers.addAll(matchedUsernames);
        return matchedUsers.stream()
                .distinct()
                .collect(Collectors.toList());
    }

    public List<Group> searchGroups(String searchQuery) {
        return groups.stream()
                .filter(a -> a.getGroupname().contains(searchQuery))
                .collect(Collectors.toList());
    }

    public int newGroup(String groupName) {
        int groupId = generateGroupId();
        addGroups(new Group(groupName, groupId));
        return groupId;
    }

    public void newUser(String username, String password, String givenName, String familyName) {
        addUser(new User(username,password,givenName,familyName));
    }

    // Used by JSON import
    public void addGroups(Group... groups) {
        // Checks if group with same ID already exist.
        Collection<Integer> usedGroupIDs = this.groups.stream()
                .map(group -> group.getGroupID())
                .collect(Collectors.toList());
        usedGroupIDs.addAll(
                Arrays.stream(groups)
                        .map(group -> group.getGroupID())
                        .collect(Collectors.toList())
        );
        for (int groupID:usedGroupIDs) {
            if (Collections.frequency(usedGroupIDs, groupID) > 1) throw new IllegalStateException("Group with same GroupID already exist.");
        }

        this.groups.addAll(Arrays.asList(groups));
    }

    // Used by JSON import
    public void addUser(User... users) {
        this.users.addAll(Arrays.asList(users));
    }

    // Generates a unique groupID with 4 digits.
    public int generateGroupId() {
        Collection<Integer> alreadyUsed = groups.stream()
                .map(group -> group.getGroupID())
                .collect(Collectors.toList());
        int groupID = 0;
        while ((groupID == 0) || (alreadyUsed.contains(groupID))) {
            groupID = ThreadLocalRandom.current().nextInt(1000,10000);
        }
        return groupID;
    }


    public Group getGroup(int groupID){
        return groups.stream()
                .filter(group -> group.getGroupID()==groupID)
                .findFirst()
                .orElse(null);
    }

    public int getGroupAmount(){
        return this.groups.size();
    }

    public static void main(String[] args) {
        Main main = new Main();
        Group gruppe7 = main.getGroup(main.newGroup("Gruppe7"));

        main.newUser("haryp", "bestePassord123", "Hary", "Pi");
        main.newUser("sanketb", "bestePassord123", "Sanket", "Be");
        main.newUser("kavus", "bestePassord123", "Lol", "Si");
        main.newUser("lol", "bestePassord123", "Njaal", "Te");

        gruppe7.addUser(main.getUser("haryp"));
        gruppe7.addUser(main.getUser("sanketb"));
        gruppe7.addUser(main.getUser("kavus"));
        gruppe7.addUser(main.getUser("lol"));
        System.out.println(main.searchUsers("sanket"));

    }


}
