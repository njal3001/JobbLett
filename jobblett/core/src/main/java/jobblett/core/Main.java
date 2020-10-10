package jobblett.core;

public class Main {

    private UserList userList = new UserList();
    private GroupList groupList = new GroupList();
    private User loggedIn = null;
    private Group activeGroup = null;


    /**
     * Initializes a new instance of Main
     * @param skipImport is used to easily create and update defaultMain.json (Only used by developers!)
     */
    /*
    public Main(boolean skipImport) {
       if (!skipImport) {
           JSONDeserialize importer = new JSONDeserialize();
           importer.updateMain(this);
       }
    }
    */
    
    /**
     * Initializes a new instance of Main with import
     */
    /*
    public Main() {
        this(false);
    }
    */


    /**
     * Saves data to main.json
     */
        /*
    public void serializeMain() {
        JSONSerialize serializer = new JSONSerialize(this, "main.json");
        serializer.exportJSON();
    } 
    */

    
    
 
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


    /**
     * Changes the instance to logged in.
     * Saves it to main.json that the user is logged in.
     * Is used by app controllers.
     * 
     * @param loggedIn the User that should be logged in
     */
    public void logIn(User loggedIn) {
        this.loggedIn = loggedIn;
        //serializeMain();
    }
    
    /**
     * Is used when an user is logging of.
     */
    public void logOut() {
        logIn(null);
    }

    /**
     * Gets the user which is currently logged in.
     * Is used by app controllers.
     * 
     * @return the logged in User
     */
    public User getLoggedIn() {
        return loggedIn;
    }
    
    /**
     * Gets the group which is currently opened in the app.
     * Is used by app controllers.
     * 
     * @return the active Group
     */
    public Group getActiveGroup() {
        return activeGroup;
    }
    
    /**
     * Sets which group is opened in the app.
     * Used by app controllers.
     * Saves which group is open to main.json.
     * 
     * @param activeGroup the Group that should be set as active
     */
    public void setActiveGroup(Group activeGroup) {
        this.activeGroup = activeGroup;
        //serializeMain();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (o instanceof Main) {
            Main main = (Main) o;
            if (!this.userList.equals(main.userList)) return false;
            if (!this.groupList.equals(main.groupList)) return false;
            if (this.loggedIn == null) {
                if (main.loggedIn != null) return false;
            }
            else {
                if (!this.loggedIn.equals(main.loggedIn)) return false;
            }
            if (this.activeGroup == null) {
                if (main.activeGroup != null) return false;
            }
            else {
                if (!this.activeGroup.equals(main.activeGroup)) return false;
            }
            return true;
        }
        else return super.equals(o);
    }

    @Override
    public int hashCode() {
        assert false : "hashCode not designed";
        return 42; // any arbitrary constant will do
    }
}
