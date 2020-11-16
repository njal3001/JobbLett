package jobblett.ui;

import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;
import jobblett.core.Group;
import jobblett.core.GroupList;
import jobblett.core.HashedPassword;
import jobblett.core.JobShift;
import jobblett.core.JobblettList;
import jobblett.core.User;
import jobblett.core.UserList;
import jobblett.json.JobblettPersistence;

public class RemoteWorkspaceAccess implements WorkspaceAccess {

  public static final String JOBBLETT_SERVICE_PATH = "jobblett";
  public static final String USER_LIST_SERVICE_PATH = "userlist";
  public static final String GROUP_LIST_SERVICE_PATH = "grouplist";

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
      String responseObjectBody = responseObject.body();

      return responseObjectBody;
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
          .POST(HttpRequest.BodyPublishers.ofString(body))
          .build();
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
          .PUT(HttpRequest.BodyPublishers.ofString(body))
          .build();
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

  @Override public Group newGroup(String groupName) {
    Group group = get(Group.class, GROUP_LIST_SERVICE_PATH + "/new/" + groupName);
    addListenerGroup(group);
    return group;
  }

  @Override public void add(User user) {
    String userString = new JobblettPersistence().writeValueAsString(user);
    put(USER_LIST_SERVICE_PATH + "/add", userString);
  }

  @Override public Group getGroup(int groupId) {
    Group group = get(Group.class, GROUP_LIST_SERVICE_PATH + "/get/" + groupId);
    addListenerGroup(group);
    return group;
  }

  @Override public User login(String userName, String passwordString) {
    String body = new HashedPassword(passwordString).toString();
    User user = post(User.class,
        USER_LIST_SERVICE_PATH + "/login/"
            + userName, body);
    user.addListener(this);
    return user;
  }

  @Override public Collection<Group> getGroups(User user) {
    String userString = new JobblettPersistence().writeValueAsString(user);
    Collection<Group> groups =
        post(GroupList.class, GROUP_LIST_SERVICE_PATH + "/getFromUsers/", userString)
        .stream()
        .collect(Collectors.toList());
    groups.forEach(this::addListenerGroup);
    return groups;
  }

  @Override public void setLists(UserList userList, GroupList groupList) {
    Collection<JobblettList> userListAndGroupList = new ArrayList<>();
    userListAndGroupList.add(userList);
    userListAndGroupList.add(groupList);
    String userListAndGroupListString =
        new JobblettPersistence().writeValueAsString(userListAndGroupList);
    getBody(JOBBLETT_SERVICE_PATH + "/setlists/" + userListAndGroupListString);
  }

  private void addListenerGroup(Group group) {
    group.addListener(this);
    group.forEach(user -> user.addListener(this));
    group.getJobShiftList().forEach(shift -> shift.addListener(this));
  }

  @Override public void propertyChange(PropertyChangeEvent evt) {
    if (evt.getSource() instanceof Group) {
      Group group = (Group) evt.getSource();
      System.out.println("The group " + group + " was modified.");
      post(Boolean.class, GROUP_LIST_SERVICE_PATH + "/replaceGroup",
          new JobblettPersistence().writeValueAsString(group));
    } else if (evt.getSource() instanceof User) {
      System.out.println("The user " + evt.getSource() + " was modified.");
    } else if (evt.getSource() instanceof JobShift) {
      JobShift jobShift = (JobShift) evt.getSource();
      System.out.println("The jobshift " + jobShift + " was modified.");
      // TODO: kreves id for å gjenkjenne Jobshift
      /*GroupList groupList = getFromServer(GroupList.class, GROUP_LIST_SERVICE_PATH);
      Group jobShiftGroup = null;
      for (Group group : groupList) {
        for (JobShift jobShift1 : group.getJobShiftList()) {
          if (jobShift.equals(jobShift1)) {
            jobShiftGroup = group;
            break;
          }
        }
        if (jobShiftGroup != null) {
          break;
        }
      }
      */
    }
  }
}
