package jobblett.ui;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface WorkspaceAccess {

  void addUser(String username, String password, String givenName, String familyName);
  
  //TODO: Tror ikke denne brukes
  boolean hasUser(String username);
  
  boolean correctPassword(String username, String password);
  
  String getUserFullName(String username);
  
  String getUserToString(String username);

  int newGroup(String groupName);
  
  //TODO: Tror ikke denne brukes
  boolean hasGroup(int groupId);

  String getGroupName(int groupId);

  Collection<Integer> getAllGroupIds(String username);

  Collection<String> getGroupUsernames(int groupId);

  void addGroupMember(int groupId, String username);

  boolean hasGroupUser(int groupId, String username);

  void addGroupAdmin(int groupId, String username);

  boolean isGroupAdmin(int groupId, String username);

  void updateJobShift(int groupId, int index, String username,
      LocalDateTime startingTime, Duration duration, String info);

  void deleteJobShift(int groupId, int index);

  void addJobShift(String username, int groupId, String jobShiftUsername,
      LocalDateTime startingTime, Duration duration, String info);

  int getJobShiftsSize(int groupId);

  //Vet ikke om dette kommer til å funke....
  List<Integer> getJobShiftIndexes(int groupId, String username);
  
  String getJobShiftUsername(int groupId, int index);

  LocalDateTime getJobShiftStartingTime(int groupId, int index);

  LocalDateTime getJobShiftEndingTime(int groupId, int index);

  String getJobShiftInfo(int groupId, int index);

  boolean jobShiftIsOutdated(int groupId, int index);
}
