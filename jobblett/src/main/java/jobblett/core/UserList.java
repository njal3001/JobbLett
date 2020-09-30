package jobblett.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class UserList implements Iterable<AbstractUser> {
    private Collection<AbstractUser> users = new ArrayList<AbstractUser>();



    /**
     * Checks whether if it exist a user with the given username.
     * @param username
     * @return the user if it exist, else null
     */
    public AbstractUser getUser(String username) {
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
    public Collection<AbstractUser> searchUsers(String searchQuery) {
        Collection<AbstractUser> matchedUsernames = users.stream()
                .filter(a -> a.getUserName().toLowerCase().contains(searchQuery.toLowerCase()))
                .collect(Collectors.toList());

        Collection<AbstractUser> matchedGivenNames = users.stream()
                .filter(a -> a.getGivenName().toLowerCase().contains(searchQuery.toLowerCase()))
                .collect(Collectors.toList());

        Collection<AbstractUser> matchedFamilyNames = users.stream()
                .filter(a -> a.getFamilyName().toLowerCase().contains(searchQuery.toLowerCase()))
                .collect(Collectors.toList());

        Collection<AbstractUser> matchedUsers = new ArrayList<AbstractUser>();
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
    public AbstractUser newUser(String username, String password, String givenName, String familyName, boolean admin) {
        if(admin) {AbstractUser adminuser = new Administrator(username,password,givenName,familyName);
            addUser(adminuser);
            return  adminuser;}
        else {AbstractUser employeeuser = new Employee(username,password,givenName,familyName);
            addUser(employeeuser);
            return employeeuser;}
    }
    
    /**
     * Optional method for adding several users into the collection of all users at once.
     * This method has not been used yet, but can be useful in the future.
     * @param users
     */
    public void addUser(AbstractUser... users) {
        for (AbstractUser user : users) {
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
    public AbstractUser login(String username, String password) {
        AbstractUser user = getUser(username);
        if (user == null) return null;
        if (! user.getPassword().equals(password)) return null;
        else return user;
    }

    @Override
    public Iterator<AbstractUser> iterator() {
        return users.iterator();
    }

    @Override
    public String toString() {
        return "UserList{" +
                "users=" + users +
                '}';
    }
}
