package bolett;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class Main {
    private Collection<User> users = new ArrayList<User>();
    private Collection<Group> groups = new ArrayList<Group>();

    public Collection<User> getUser(String username) {
        return users.stream().anyMatch(a -> a.);
    }
}
