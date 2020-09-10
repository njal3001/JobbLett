package bolett;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    private Collection<User> users = new ArrayList<User>();
    private Collection<Group> groups = new ArrayList<Group>();

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

    private void addUser(User... users) {
        for (User user: users) {
            this.users.add(user);
        }
    }

    public static void main(String[] args) {
        Main main = new Main();

        User hary = new User("haryp", "bestepassord123", "Hary", "Pi");
        User sanket = new User("sanketb", "bestepassord123", "Sanket", "Be");
        User kavu = new User("kavus", "bestepassord123", "Lol", "Si");
        User njaal = new User("lol", "bestepassord123", "Njaal", "Te");

        Group gruppe7 = new Group("gruppe7");
        gruppe7.addUser(hary);
        gruppe7.addUser(sanket);
        gruppe7.addUser(kavu);
        gruppe7.addUser(njaal);
        main.addUser(hary, sanket, kavu, njaal);
        System.out.println(main.searchUsers("sanket"));

    }


}
