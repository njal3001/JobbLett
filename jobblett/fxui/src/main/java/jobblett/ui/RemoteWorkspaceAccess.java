package jobblett.ui;

import static jobblett.core.Group.checkGroupName;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import jobblett.core.Group;
import jobblett.core.GroupList;
import jobblett.core.HashedPassword;
import jobblett.core.JobShift;
import jobblett.core.JobShiftList;
import jobblett.core.User;
import jobblett.core.UserList;
import jobblett.json.JobblettPersistence;


public class RemoteWorkspaceAccess implements WorkspaceAccess {

  public static final String JOBBLETT_SERVICE_PATH = "jobblett";
  public static final String USER_LIST_RESOURCE_PATH = "userlist";
  public static final String GROUP_LIST_RESOURCE_PATH = "grouplist";
  public static final String JOB_SHIFT_LIST_RESOURCE_PATH = "shifts";

  private final URI endpointBaseUri;

  public RemoteWorkspaceAccess(URI endpointBaseUri) {
    this.endpointBaseUri = endpointBaseUri;
  }

  private String getBody(String url) {
    HttpRequest requestObject = null;
    try {
      requestObject = HttpRequest.newBuilder(endpointBaseUri.resolve(new URI(url)))
              .header("Accept", "application/json").build();
      HttpResponse<String> responseObject =
          HttpClient.newBuilder().build().send(requestObject, HttpResponse.BodyHandlers.ofString());
      return responseObject.body();
    } catch (URISyntaxException | IOException | InterruptedException e) {
      e.printStackTrace();
    }
    return null;
  }

  private <T> T post(Class<T> t, String urlString, String body) {
    try {
      HttpRequest requestObject =
          HttpRequest.newBuilder(endpointBaseUri.resolve(new URI(urlString)))
              .header("Accept", "application/json")
              .header("Content-Type", "application/json")
              .POST(HttpRequest.BodyPublishers.ofString(body)).build();
      HttpResponse<String> responseObject =
          HttpClient.newBuilder().build().send(requestObject, HttpResponse.BodyHandlers.ofString());
      String responseObjectBody = responseObject.body();
      T object = new JobblettPersistence().readValue(t, responseObjectBody);
      return object;

    } catch (IOException | InterruptedException | URISyntaxException e) {
      e.printStackTrace();
    }
    return null;
  }

  private void put(String urlString, String body) {
    try {
      HttpRequest requestObject =
          HttpRequest.newBuilder(endpointBaseUri.resolve(new URI(urlString)))
              .header("Accept", "application/json")
              .header("Content-Type", "application/json")
              .PUT(HttpRequest.BodyPublishers.ofString(body)).build();
      HttpClient.newBuilder().build().send(requestObject, HttpResponse.BodyHandlers.ofString());

    } catch (IOException | InterruptedException | URISyntaxException e) {
      e.printStackTrace();
    }
  }

  private <T> T get(Class<T> t, String url) {
    String responseObjectBody = getBody(url);
    T o = new JobblettPersistence().readValue(t, responseObjectBody);
    return o;
  }

  private void checkUsername(String username) {
    if (!hasUser(username)) {
      throw new IllegalArgumentException("No user with the username: " + username);
    }
  }

  /**
   * Adds a new User with the given parameters.
   *
   * @param username of the user
   * @param password of the user
   * @param givenName of the user
   * @param familyName of the user
   */
  @Override
  public void addUser(String username, String password, String givenName, String familyName) {
    if (hasUser(username)) {
      new UserList().optionalAlreadyExists();
    }
    User user = new User(username, new HashedPassword(password), givenName, familyName);
    String serializedUser = new JobblettPersistence().writeValueAsString(user);
    put(USER_LIST_RESOURCE_PATH + "/add", serializedUser);
  }

  /**
   * Checks if an user with the given username exists.
   *
   * @param username of a user
   * @return true if user with username exists, false otherwise
   */
  @Override
  public boolean hasUser(String username) {
    return post(Boolean.class, USER_LIST_RESOURCE_PATH + "/exist", username);
  }

  private User getUser(String username) {
    checkUsername(username);
    return get(User.class, USER_LIST_RESOURCE_PATH + "/get/" + username);
  }

  /**
   * Checks if there is an user with the given username and checks if password
   * matches.
   *
   * @param username username of the user
   * @param passwordString password to be checked
   * @return true if password of user with given username matches the given password
   */
  @Override
  public boolean correctPassword(String username, String passwordString) {
    if (!hasUser(username)) {
      return false;
    }
    HashedPassword password = new HashedPassword(passwordString);
    return getUser(username).getPassword().matches(password);
  }

  /**
   * Gets the full name of the user with the given username.
   *
   * @param username of the user
   * @return the full name of the user
   */
  @Override
  public String getUserFullName(String username) {
    User user = getUser(username);
    return user.getGivenName() + " " + user.getFamilyName();
  }

  /**
   * Gets the toString of the user with the given username.
   *
   * @param username of the user
   * @return toString of the user
   */
  @Override
  public String getUserToString(String username) {
    return getUser(username).toString();
  }

  private void checkGroupId(int groupId) {
    if (!hasGroup(groupId)) {
      throw new IllegalArgumentException("No group with the ID: " + groupId);
    }
  }

  /**
   * Creates a new group with the given name.
   *
   * @param groupName the name of the new group
   * @return Id of the newly created group
   */
  @Override
  public int newGroup(String groupName) {
    checkGroupName(groupName);
    Group group =  post(Group.class, GROUP_LIST_RESOURCE_PATH + "/new", groupName);
    return group.getGroupId();
  }

  /**
   * Checks if there exists a group with the given id.
   *
   * @param groupId of a group
   * @return true if group with given id exists, false otherwise
   */
  @Override
  public boolean hasGroup(int groupId) {
    return get(Boolean.class, GROUP_LIST_RESOURCE_PATH + "/exist/" + groupId);
  }

  private Group getGroup(int groupId) {
    checkGroupId(groupId);
    return get(Group.class, GROUP_LIST_RESOURCE_PATH + "/get/" + groupId);
  }

  /**
   * Gets the group name of the group with the given id.
   *
   * @param groupId of the group
   * @return the name of the group
   */
  @Override
  public String getGroupName(int groupId) {
    return getGroup(groupId).getGroupName();
  }

  /**
   * Gets a collection of all group ids of groups which contain
   * the user with the given username.
   *
   * @param username of the user
   * @return a collection of group ids
   * 
   */
  @Override
  public Collection<Integer> getAllGroupIds(String username) {
    /*GroupList groupList = get(GroupList.class, GROUP_LIST_RESOURCE_PATH);
    return groupList.stream()
        .filter(group -> {
          boolean containsUser = false;
          for (User user : group) {
            if (user.getUsername().equals(username)) {
              containsUser = true;
              break;
            }
          }
          return containsUser;
        })
        .map(Group::getGroupId)
        .collect(Collectors.toList());*/
    return post(GroupList.class, GROUP_LIST_RESOURCE_PATH + "/getFromUsers", username)
        .stream()
        .map(Group::getGroupId)
        .collect(Collectors.toList());
  }

  /**
   * Gets a collection of all usernames of users in the group
   * with the given id.
   *
   * @param groupId of the group
   * @return a collection of usernames
   */
  @Override
  public Collection<String> getGroupUsernames(int groupId) {
    Group group = getGroup(groupId);
    Collection<String> userNames = new ArrayList<>();
    group.forEach(user -> userNames.add(user.getUsername()));
    return userNames;
  }

  /**
   * Adds the user with the given username to the group
   * with the given id.
   *
   * @param groupId of the group
   * @param username of the user
   */
  @Override
  public void addGroupMember(int groupId, String username) {
    User user = getUser(username);

    // Throwing error if user already exist.
    // Doing it by adding the user to a deserialized copy
    getGroup(groupId).addUser(user);

    String serializedUser = new JobblettPersistence().writeValueAsString(user);
    put(GROUP_LIST_RESOURCE_PATH + "/get/" + groupId + "/add", serializedUser);
  }

  /**
   * Checks if the group with given groupId contains the user with
   * the given username.
   *
   * @param groupId of the group
   * @param username of the user
   * @return true if the group contains the user, false otherwise
   */
  @Override
  public boolean hasGroupUser(int groupId, String username) {
    Group group = getGroup(groupId);
    return group.getUser(username) != null;
  }

  /**
   * Adds the user with the given username as an admin in the group
   * with the given groupId.
   *
   * @param groupId of the group
   * @param username of the user
   */
  @Override
  public void addGroupAdmin(int groupId, String username) {
    get(Boolean.class, GROUP_LIST_RESOURCE_PATH + "/get/" + groupId + "/addAdmin/" + username);
  }

  /**
   * Checks if the user with the given username is an admin of the 
   * group with the given groupId.
   *
   * @param groupId of the group
   * @param username of the user
   * @return true if user is admin in the group, false otherwise
   */
  @Override
  public boolean isGroupAdmin(int groupId, String username) {
    return get(Boolean.class,
        GROUP_LIST_RESOURCE_PATH + "/get/" + groupId + "/isAdmin/" + username);
  }

 
  /**
   * An user with the given username deletes the jobshift with the given index
   * in the group with the given groupId.
   *
   * @param adminUsername username of the user
   * @param groupId of the group
   * @param index of the jobshift
   * @throws IllegalArgumentException if the user is not an admin in the group
   */
  @Override
  public void deleteJobShift(String adminUsername, int groupId, int index) {
    get(Boolean.class, GROUP_LIST_RESOURCE_PATH + "/get/" + groupId + "/"
        + JOB_SHIFT_LIST_RESOURCE_PATH + "/remove/" + adminUsername + "/" + index);
  }

  /**
   * An user with the given username adds a jobshift with the given values
   * in the group with the given groupId. 
   * Throws an exception if the user that adds the shift
   * is not an admin in the group or the jobshift user is not a member
   * of the group.
   *
   * @param adminUsername username of the user that adds the shift
   * @param groupId of the group
   * @param jobShiftUsername username of the user with the jobshift
   * @param startingTime of the jobshift
   * @param duration of the jobshift
   * @param info of the jobshift
   * @throws IllegalArgumentException if users are not valid
   */
  @Override
  public void addJobShift(String adminUsername, int groupId, String jobShiftUsername,
      LocalDateTime startingTime, Duration duration, String info) {
    
    JobShift jobShift = new JobShift(getUser(jobShiftUsername), startingTime, duration, info);
    String serializedJobshift = new JobblettPersistence().writeValueAsString(jobShift);
    put(GROUP_LIST_RESOURCE_PATH + "/get/" + groupId + "/"
        + JOB_SHIFT_LIST_RESOURCE_PATH + "/add/" + adminUsername, serializedJobshift);
  }

  /**
   * Gets the number of jobshifts for the group with
   * the given groupId.
   */
  @Override
  public int getJobShiftsSize(int groupId) {
    return get(JobShiftList.class,
        GROUP_LIST_RESOURCE_PATH + "/get/" + groupId + "/" + JOB_SHIFT_LIST_RESOURCE_PATH).size();
  }

  /** Gets a list of the indexes of the jobshifts in the group with the given groupId
   * filtered by the user with the given username.
   *
   * @param groupId of the group
   * @param username of the user
   * @return a filtered list of the jobshift indexes
   */
  @Override
  public List<Integer> getJobShiftIndexes(int groupId, String username) {
    Group group = getGroup(groupId);
    User user = group.getUser(username);
    JobShiftList jobShiftList = group.getJobShiftList();
    return jobShiftList
        .stream()
        .filter(shift -> shift.getUser().getUsername().equals(user.getUsername()))
        .map(jobShiftList::indexOf)
        .collect(Collectors.toList());
  }

  private JobShift getJobShift(int groupId, int index) {
    return get(
        JobShift.class,
        GROUP_LIST_RESOURCE_PATH + "/get/" + groupId + "/"
            + JOB_SHIFT_LIST_RESOURCE_PATH + "/get/" + index
    );
  }

  /**
   * Gets the username of the user for the jobshift with the given index in
   * the group with the given groupId.
   *
   * @param groupId of the group
   * @param index of the jobshift
   * @return the username of jobshifts user
   */
  @Override
  public String getJobShiftUsername(int groupId, int index) {
    return getJobShift(groupId, index).getUser().getUsername();
  }


  /**
   * Gets the starting time of the jobshift with the given index in
   * the group with the given groupId.
   *
   * @param groupId of the group
   * @param index of the jobshift
   * @return the starting time of the jobshift
   */
  @Override
  public LocalDateTime getJobShiftStartingTime(int groupId, int index) {
    return getJobShift(groupId, index).getStartingTime();
  }

  /**
   * Gets the ending time of the jobshift with the given index in
   * the group with the given groupId.
   *
   * @param groupId of the group
   * @param index of the jobshift
   * @return the ending time of the jobshift
   */
  @Override
  public LocalDateTime getJobShiftEndingTime(int groupId, int index) {
    return getJobShift(groupId, index).getEndingTime();
  }

  /**
   * Gets the info of the jobshift with the given index in
   * the group with the given groupId.
   *
   * @param groupId of the group
   * @param index of the jobshift
   * @return the info of the jobshift
   */
  @Override
  public String getJobShiftInfo(int groupId, int index) {
    return getJobShift(groupId, index).getInfo();
  }

  /**
   * Checks if the jobshift with the given index in the group with the given
   * groupId, is outdated.
   *
   * @param groupId of the group
   * @param index of the jobshift
   * @return true if the jobshift is outdated, false otherwise
   */
  @Override
  public boolean jobShiftIsOutdated(int groupId, int index) {
    return getJobShift(groupId, index).isOutDated();
  }

  /**
   * Deletes alle outdated jobshifts in the group with the 
   * given groupId.
   *
   * @param groupId of the group
   */
  @Override
  public void deleteOutdatedJobShift(int groupId) {
    put(GROUP_LIST_RESOURCE_PATH + "/get/" + groupId + "/"
        + JOB_SHIFT_LIST_RESOURCE_PATH + "/deleteOutdated", "");
  }
}
