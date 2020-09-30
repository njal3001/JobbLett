package jobblett.core;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class UserList implements Iterable<AbstractUser> {
    private Collection<AbstractUser> users = new ArrayList<>();


    /**
     * Checks whether if it exist a user with the given username.
     * @param username the username to be checked
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
     * @param searchQuery the searchQuery for searching
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

        Collection<AbstractUser> matchedUsers = new ArrayList<>();
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
     * @param username username
     * @param password password of the new User
     * @param givenName given name of the new User
     * @param familyName family name of the new User
     * @return the created user
     *
     * @deprecated Use addUser(new User(...)) instead.
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
     * @param users list of users to be added
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
     * @param username username used to check
     * @param password password used to check
     * @return the user if logged in, else null
     */
    public AbstractUser login(String username, String password) {
        AbstractUser user = getUser(username);
        if (user == null) return null;
        if (! user.matchesPassword(password)) return null;
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
