package jobblett.core;

public class Administrator extends AbstractUser{

    private GroupList groupList;
    private Employee employee;
    public Administrator(String userName, String password, String givenName, String familyName){
        super(userName, password, givenName, familyName);
        groupList = new GroupList();
    }

    private void addOrRemoveEmployee(Group group, Employee employee){
        if(!groupList.getGroups(employee).contains(group))
            if(groupList.getGroup(group.getGroupID())!=null)
                group.addUser(employee);
    }

    private void removeEmployeeFromGroup(Group group, Employee employee){
        if(groupList.getGroups(employee).contains(group))
            if(groupList.getGroup(group.getGroupID())!=null)
                group.removeUser(employee);

    }

    public void setShiftForEmployee(Employee employee){

    }


}
