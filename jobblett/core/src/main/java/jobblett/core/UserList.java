package jobblett.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public class UserList implements Iterable<User> {
    
    private Collection<User> users = new ArrayList<>();


    /**
     * Checks whether if it exist a user with the given username.
     * @param username the username to be checked
     * @return the user if it exist, else null
     */
    public User getUser(String username) {
        return users.stream()
                .filter(a -> a.getUserName().equals(username))
                .findAny()
                .orElse(null);
    }    
    
    /**
     * Optional method for adding several users into the collection of all users at once.
     * This method has not been used yet, but can be useful in the future.
     * @param users list of users to be added
     */
    public void addUser(User... users) {
        for (User user : users) {
            if (getUser(user.getUserName()) != null)
                throw new IllegalArgumentException("User with the same username already exist.");
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
    public User login(String username, String password) {
        User user = getUser(username);
        if (user == null || !user.matchesPassword(password)) 
            return null;
        else 
            return user;
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

    @Override
    public boolean equals(Object o) {
        if (o instanceof UserList) {
            UserList newUsers = (UserList) o;
            return users.equals(newUsers.users);
        }
        else return super.equals(o);
    }
}
