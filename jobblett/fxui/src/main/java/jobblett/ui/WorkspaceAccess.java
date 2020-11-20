package jobblett.ui;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface WorkspaceAccess {

  void addUser(String username, String password, String givenName, String familyName);
  
  boolean hasUser(String username);
  
  boolean correctPassword(String username, String password);
  
  String getUserFullName(String username);
  
  String getUserToString(String username);

  int newGroup(String groupName);
  
  boolean hasGroup(int groupId);

  String getGroupName(int groupId);

  Collection<Integer> getAllGroupIds(String username);

  Collection<String> getGroupUsernames(int groupId);

  void addGroupMember(int groupId, String username);

  boolean hasGroupUser(int groupId, String username);

  void addGroupAdmin(int groupId, String username);

  boolean isGroupAdmin(int groupId, String username);

  void deleteJobShift(String adminUsername, int groupId, int index);

  void addJobShift(String adminUsername, int groupId, String jobShiftUsername,
       LocalDateTime startingTime, Duration duration, String info);

  int getJobShiftsSize(int groupId);

  List<Integer> getJobShiftIndexes(int groupId, String username);
  
  String getJobShiftUsername(int groupId, int index);

  LocalDateTime getJobShiftStartingTime(int groupId, int index);

  LocalDateTime getJobShiftEndingTime(int groupId, int index);

  String getJobShiftInfo(int groupId, int index);

  boolean jobShiftIsOutdated(int groupId, int index);

  void deleteOutdatedJobShift(int groupId);
}
