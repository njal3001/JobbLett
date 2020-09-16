package jobblett.core;

import jobblett.json.JSONDeserialize;
import jobblett.json.JSONSerialize;

public class Main {
    private UserList userList = new UserList();
    private GroupList groupList = new GroupList();
    private User loggedIn = null;
    private Group activeGroup = null;

    public Main() {
        this(false);
    }


    public Main(boolean skipImport) {
       if (!skipImport) {
           JSONDeserialize importer = new JSONDeserialize();
           importer.updateMain(this);
       }
    }

    public void serializeMain() {
        JSONSerialize serializer = new JSONSerialize(this, "src/main/resources/jobblett/json/main.json");
        serializer.exportJSON();
    }

    public GroupList getGroupList() {
        return groupList;
    }

    public UserList getUserList() {
        return userList;
    }

    @Override
    public String toString() {
        return "Main{" +
                "userList=" + userList +
                ", groupList=" + groupList +
                '}';
    }

    public static void main(String[] args) {
        Main main = new Main();
        System.out.println(main);

    }

    public void logIn(User loggedIn) {
        this.loggedIn = loggedIn;
        serializeMain();
    }

    public void logOut() {
        logIn(null);
    }

    public User getLoggedIn() {
        return loggedIn;
    }

    public Group getActiveGroup() {
        return activeGroup;
    }

    public void setActiveGroup(Group activeGroup) {
        this.activeGroup = activeGroup;
        serializeMain();
    }
}
