package jobblett.core;

public class Employee extends AbstractUser{


    /**
     * Checks if the parameters are valid before creating instance a User.
     * Allows JSON to create empty Users.
     *
     * @param userName
     * @param password
     * @param givenName
     * @param familyName
     */

    private GroupList groupList;
    private Employee employee;
    public Employee(String userName, String password, String givenName, String familyName) {
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
