package jobblett.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Collectors;

public class UserList implements Iterable<User> {
    private Collection<User> users = new ArrayList<User>();


    /**
     * Checks whether if it exist a user with the given username.
     * @param username
     * @return the user if it exist, else null
     */
    public User getUser(String username) {
        return users.stream()
                .filter(a -> a.getUserName().equals(username))
                .findAny()
                .orElse(null);
    }
    
    /**
     * Searches for other users.
     * Searches in all users' username, givenname and familyname.
     * @param searchQuery
     * @return A collections with all the matching users.
     */
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
    
    /**
     * Creates a new user.
     * Adds the user to the collections of all the Users
     * @param username
     * @param password
     * @param givenName
     * @param familyName
     * @return the created user
     */
    public User newUser(String username, String password, String givenName, String familyName) {
        User user = new User(username,password,givenName,familyName);
        addUser(user);
        return user;
    }
    
    /**
     * Optional method for adding several users into the collection of all users at once.
     * This method has not been used yet, but can be useful in the future.
     * @param users
     */
    public void addUser(User... users) {
        for (User user : users) {
            if (getUser(user.getUserName())!=null) throw new IllegalArgumentException("User with the same username already exist.");
        }

        this.users.addAll(Arrays.asList(users));
    }

    /**
     * Lets the user log into their account
     * Checks whether the username and password matches an existing user, before logging in
     * @param username
     * @param password
     * @return the user if logged in, else null
     */
    public User login(String username, String password) {
        User user = getUser(username);
        if (user == null) return null;
        if (! user.matchesPassword(password)) return null;
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
