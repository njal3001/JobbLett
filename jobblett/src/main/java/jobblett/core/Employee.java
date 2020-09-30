package jobblett.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Employee extends AbstractUser{

    private GroupList groupList;
    private Employee employee;

    @JsonCreator
    public Employee(@JsonProperty("userName") String userName){
        super(userName, "DefaultPassword123", "DefaultGivenName", "DefaultFamilyName");
    }

    /**
     * Checks if the parameters are valid before creating instance a User.
     * Allows JSON to create empty Users.
     *
     * @param userName
     * @param password
     * @param givenName
     * @param familyName
     */
    public Employee(String userName, String password, String givenName, String familyName){
        super(userName, password, givenName, familyName);
        this.groupList = new GroupList();
    }

    private void leaveGroup(Group group){
        if(this.groupList.getGroups(employee).contains(group))
            this.employee.leaveGroup(group);
    }

    private void removeShift(){

    }
}
