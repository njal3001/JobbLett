package bolett;

import java.util.ArrayList;
import java.util.Collection;

public class Group {

    private String groupname;
    private Collection<User> groupmembers = new ArrayList<User>();
    private static int groupdID = 1;

    public Group (String groupname){
      //  checkExistingGroupName(groupname);
        checkGroupName(groupname);
        this.groupname = groupname;
    }

    public Group(String groupname, int groupID){
        //  checkExistingGroupName(groupname);
        this.groupname = groupname;
        setGroupID(groupID);

    }

    private void addUser(User userp){
        checkExistingUser(user);
        this.groupmembers.add(user);
    }

    private void removeUser(User user){
            this.groupmembers.remove(user);
    }

    private void checkExistingUser(User user){
         if(this.groupmembers.contains(user)){
             throw new IllegalArgumentException("This user is already in the group");
         }
    }

    //Pretending that Collection<Group> groups = new ArrayList<Group>(); exists in main class
/*   private void checkExistingGroupName(String groupname){
       if(gr.stream().anyMatch(gorup -> gorup.groupname.equals(groupname))){
           throw new IllegalArgumentException("Groupname already exists");
       }
   }*/

   private void checkGroupName(String groupname){
       if(groupname.length() <2){
           throw new IllegalArgumentException("Grouname length must be atleast 2 lettars");
       }
   }

   private void setGroupID(int groupdID){
       //Not yet implemented
   }

    public int getGroupSize(){
        return this.groupmembers.size();
    }

    public String getGroupname(){
        return this.groupname;
    }

    @Override
    public String toString(){
       return this.groupname;
    }




}
