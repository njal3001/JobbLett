package jobblett.restapi;

import java.util.ArrayList;
import java.util.Collection;
import jobblett.core.Group;
import jobblett.core.GroupList;
import jobblett.core.JobShift;
import jobblett.core.User;
import jobblett.core.UserList;
import org.slf4j.Logger;

public abstract class RestApiClass {

  protected abstract Logger logger();

  protected void debug(String logInfo) {
    logger().debug(logInfo);
    System.out.println("[LOG:DEBUG] " + logInfo);
  }

  // TODO: Should be removed
  protected GroupList correctGroupList(GroupList oldGroupList, UserList userList) {
    GroupList groupList = new GroupList();
    for (Group group : oldGroupList) {
      // Remove duplicate users and replace with original users
      Collection<User> duplicateUsers = new ArrayList<>();
      for (User user : group) {
        if (userList.get(user.getUsername()) != null) {
          duplicateUsers.add(user);
        } else {
          System.out.println(
              "The user " + user.toString() + " is not contained in userList but contained in "
                  + group.toString() + ".");
        }
      }
      for (User user : duplicateUsers) {
        User originalUser = userList.get(user.getUsername());
        group.removeUser(user);
        group.addUser(originalUser);
      }

      Collection<User> duplicateAdmins = new ArrayList<>();
      for (User user : group.getAdmins()) {
        if (userList.get(user.getUsername()) != null) {
          duplicateAdmins.add(user);
        } else {
          System.out.println(
              "The user " + user.toString() + " is not contained in userList but contained in "
                  + group.toString() + ".");
        }
      }
      for (User user : duplicateAdmins) {
        User originalUser = userList.get(user.getUsername());
        group.removeAdmin(user);
        group.addAdmin(originalUser);
      }


      // Remove duplicate users in JobShifts
      for (JobShift shift : group.getJobShiftList()) {
        User user = shift.getUser();
        if (user == null) {
          break;
        }
        User originalUser = userList.get(user.getUsername());
        if (originalUser != null) {
          shift.setUser(originalUser);
        } else {
          System.out.println(
              "The user " + user.toString() + " is not contained in userList but contained in "
                  + shift.toString() + ".");
        }
      }
      groupList.add(group);
    }
    return groupList;
  }
}
