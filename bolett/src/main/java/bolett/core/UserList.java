package bolett.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Collectors;

public class UserList implements Iterable<User> {
    private Collection<User> users = new ArrayList<User>();

    // Used by JSON Deserialization
    public void setUsers(Collection<User> users) {
        this.users = users;
    }

    // Used by JSON Serialization
    public Collection<User> getUsers() {
        return users.stream().collect(Collectors.toList());
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

    public User newUser(String username, String password, String givenName, String familyName) {
        User user = new User(username,password,givenName,familyName);
        addUser(user);
        return user;
    }

    public void addUser(User... users) {
        for (User user : users) {
            if (getUser(user.getUserName())!=null) throw new IllegalArgumentException("User with the same username already exist.");
        }

        this.users.addAll(Arrays.asList(users));
    }

    public User login(String username, String password) {
        User user = getUser(username);
        if (user == null) return null;
        if (! user.getPassword().equals(password)) return null;
        else return user;
    }

    @Override
    public Iterator<User> iterator() {
        return users.iterator();
    }

    @Override
    public String toString() {
        return "UserList{" +
                "users=" + users +
                '}';
    }
}
