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

  //TODO: JAVAAADOC

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


  @Override
  public void addUser(String username, String password, String givenName, String familyName) {
    if (hasUser(username)) {
      new UserList().optionalAlreadyExists();
    }
    User user = new User(username, new HashedPassword(password), givenName, familyName);
    String serializedUser = new JobblettPersistence().writeValueAsString(user);
    put(USER_LIST_RESOURCE_PATH + "/add", serializedUser);
  }

  @Override
  public boolean hasUser(String username) {
    return post(Boolean.class, USER_LIST_RESOURCE_PATH + "/exist", username);
  }

  private User getUser(String username) {
    checkUsername(username);
    return get(User.class, USER_LIST_RESOURCE_PATH + "/get/" + username);
  }

  @Override
  public boolean correctPassword(String username, String passwordString) {
    if (!hasUser(username)) {
      return false;
    }
    HashedPassword password = new HashedPassword(passwordString);
    return getUser(username).getPassword().matches(password);
  }

  @Override
  public String getUserFullName(String userName) {
    User user = getUser(userName);
    return user.getGivenName() + " " + user.getFamilyName();
  }

  @Override
  public String getUserToString(String userName) {
    return getUser(userName).toString();
  }

  private void checkGroupId(int groupId) {
    if (!hasGroup(groupId)) {
      throw new IllegalArgumentException("No group with the ID: " + groupId);
    }
  }

  @Override
  public int newGroup(String groupName) {
    checkGroupName(groupName);
    Group group =  post(Group.class, GROUP_LIST_RESOURCE_PATH + "/new", groupName);
    return group.getGroupId();
  }

  @Override
  public boolean hasGroup(int groupId) {
    return get(Boolean.class, GROUP_LIST_RESOURCE_PATH + "/exist/" + groupId);
  }

  private Group getGroup(int groupId) {
    checkGroupId(groupId);
    return get(Group.class, GROUP_LIST_RESOURCE_PATH + "/get/" + groupId);
  }

  @Override
  public String getGroupName(int groupId) {
    return getGroup(groupId).getGroupName();
  }

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

  @Override
  public Collection<String> getGroupUsernames(int groupId) {
    Group group = getGroup(groupId);
    Collection<String> userNames = new ArrayList<>();
    group.forEach(user -> userNames.add(user.getUsername()));
    return userNames;
  }

  @Override
  public void addGroupMember(int groupId, String userName) {
    User user = getUser(userName);

    // Throwing error if user already exist.
    // Doing it by adding the user to a deserialized copy
    getGroup(groupId).addUser(user);

    String serializedUser = new JobblettPersistence().writeValueAsString(user);
    put(GROUP_LIST_RESOURCE_PATH + "/get/" + groupId + "/add", serializedUser);
  }

  @Override
  public boolean hasGroupUser(int groupId, String username) {
    Group group = getGroup(groupId);
    return group.getUser(username) != null;
  }

  @Override
  public void addGroupAdmin(int groupId, String username) {
    get(Boolean.class, GROUP_LIST_RESOURCE_PATH + "/get/" + groupId + "/addAdmin/" + username);
  }

  @Override
  public boolean isGroupAdmin(int groupId, String username) {
    return get(Boolean.class,
        GROUP_LIST_RESOURCE_PATH + "/get/" + groupId + "/isAdmin/" + username);
  }

 
  @Override
  public void deleteJobShift(String adminUsername, int groupId, int index) {
    get(Boolean.class, GROUP_LIST_RESOURCE_PATH + "/get/" + groupId + "/"
        + JOB_SHIFT_LIST_RESOURCE_PATH + "/remove/" + adminUsername + "/" + index);
  }

  @Override
  public void addJobShift(String adminUsername, int groupId, String jobShiftUsername,
      LocalDateTime startingTime, Duration duration, String info) {
    
    JobShift jobShift = new JobShift(getUser(jobShiftUsername), startingTime, duration, info);
    String serializedJobshift = new JobblettPersistence().writeValueAsString(jobShift);
    put(GROUP_LIST_RESOURCE_PATH + "/get/" + groupId + "/"
        + JOB_SHIFT_LIST_RESOURCE_PATH + "/add/" + adminUsername, serializedJobshift);
  }

  @Override
  public int getJobShiftsSize(int groupId) {
    return get(JobShiftList.class,
        GROUP_LIST_RESOURCE_PATH + "/get/" + groupId + "/" + JOB_SHIFT_LIST_RESOURCE_PATH).size();
  }

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

  @Override
  public String getJobShiftUsername(int groupId, int index) {
    return getJobShift(groupId, index).getUser().getUsername();
  }

  @Override
  public LocalDateTime getJobShiftStartingTime(int groupId, int index) {
    return getJobShift(groupId, index).getStartingTime();
  }

  @Override
  public LocalDateTime getJobShiftEndingTime(int groupId, int index) {
    return getJobShift(groupId, index).getEndingTime();
  }

  @Override
  public String getJobShiftInfo(int groupId, int index) {
    return getJobShift(groupId, index).getInfo();
  }

  @Override
  public boolean jobShiftIsOutdated(int groupId, int index) {
    return getJobShift(groupId, index).isOutDated();
  }

  @Override
  public void deleteOutdatedJobShift(int groupId) {
    put(GROUP_LIST_RESOURCE_PATH + "/get/" + groupId + "/"
        + JOB_SHIFT_LIST_RESOURCE_PATH + "/deleteOutdated", "");
  }
}
