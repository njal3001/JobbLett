package bolett;

import java.util.ArrayList;
import java.util.Collection;

public class Group {

    private String groupname;
    private Collection<User> groupmembers = new ArrayList<User>();
    private final int groupID;

    public Group(String groupname, int groupID) {
        // Checks if GroupIDs are 4 digit.
        if ((groupID<1000)&&(groupID>=10000)) throw new IllegalArgumentException("GroupID should be 4 digit");

        changeGroupName(groupname);
        this.groupID = groupID;

    }

    public void addUser(User user){
        checkExistingUser(user);
        this.groupmembers.add(user);
    }

    public void removeUser(User user){
            this.groupmembers.remove(user);
    }

    private void checkExistingUser(User user) {
        if (this.groupmembers.contains(user)) {
            throw new IllegalArgumentException("This user is already in the group");
        }
    }

    //Pretending that Collection<Group> groups = new ArrayList<Group>(); exists in main class
/*   private void checkExistingGroupName(String groupname){
       if(gr.stream().anyMatch(gorup -> gorup.groupname.equals(groupname))){
           throw new IllegalArgumentException("Groupname already exists");
       }
   }*/

    public void changeGroupName(String groupname) {
        checkGroupName(groupname);
        this.groupname = groupname;
    }

    private void checkGroupName(String groupname) {
        if (groupname.trim().length() < 2) {
            throw new IllegalArgumentException("Grouname length must be atleast 2 lettars");
        }
    }

/*    private void setGroupID(int groupdID) {
        //Not yet implemented
        for (int i = 0; i< ; i++) {

        }

    }*/

    public int getGroupSize() {
        return this.groupmembers.size();
    }

    public String getGroupname() {
        return this.groupname;
    }
    
    public Collection<User> getGroupmembers() {
		return groupmembers;
	}

    public int getGroupID() {
        return groupID;
    }

    @Override
    public String toString() {
        return this.groupname;
    }

}


