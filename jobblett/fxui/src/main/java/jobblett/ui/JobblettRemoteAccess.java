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

public class JobblettRemoteAccess implements JobblettAccess {

  public static final String JOBBLETT_SERVICE_PATH = "jobblett";
  public static final String USER_LIST_SERVICE_PATH = "userlist";
  public static final String GROUP_LIST_SERVICE_PATH = "grouplist";

  private final URI endpointBaseUri;

  public JobblettRemoteAccess(URI endpointBaseUri) {
    this.endpointBaseUri = endpointBaseUri;
  }

  private String getBodyFromServer(String url) {
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

  private <T> T postFromServer(Class<T> t, String urlString, String body) {
    HttpRequest requestObject = null;
    try {
      requestObject = HttpRequest.newBuilder(endpointBaseUri.resolve(new URI(urlString)))
          .header("Accept", "application/json")
          .header("Content-Type", "application/json")
          .POST(HttpRequest.BodyPublishers.ofString(body))
          .build();
      HttpResponse<String> responseObject =
          HttpClient.newBuilder().build().send(requestObject, HttpResponse.BodyHandlers.ofString());
      String responseObjectBody = responseObject.body();
      System.out.println(responseObjectBody);
      T object = new JobblettPersistence().readValue(t, responseObjectBody);
      return object;

    } catch (IOException | InterruptedException | URISyntaxException e) {
      e.printStackTrace();
    }
    return null;
  }

  private <T> T getFromServer(Class<T> t, String url) {
    String responseObjectBody = getBodyFromServer(url);
    T o = new JobblettPersistence().readValue(t, responseObjectBody);
    return o;
  }

  @Override public Group newGroup(String groupName) {
    Group group = getFromServer(Group.class, GROUP_LIST_SERVICE_PATH + "/new/" + groupName);
    addListenerGroup(group);
    return group;
  }

  @Override public void add(User user) {
    String userString = new JobblettPersistence().writeValueAsString(user);
    getBodyFromServer(USER_LIST_SERVICE_PATH + "/add/" + userString);
  }

  @Override public Group getGroup(int groupId) {
    Group group = getFromServer(Group.class, GROUP_LIST_SERVICE_PATH + "/get/" + groupId);
    addListenerGroup(group);
    return group;
  }

  @Override public User login(String userName, String passwordString) {
    String body = passwordString;
    User user = postFromServer(User.class,
        USER_LIST_SERVICE_PATH + "/login/"
            + userName, body);
    user.addListener(this);
    return user;
  }

  @Override public Collection<Group> getGroups(User user) {
    String userString = new JobblettPersistence().writeValueAsString(user);
    Collection<Group> groups =
        postFromServer(GroupList.class, GROUP_LIST_SERVICE_PATH + "/getFromUsers/", userString)
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
    getBodyFromServer(JOBBLETT_SERVICE_PATH + "/setlists/" + userListAndGroupListString);
  }

  private void addListenerGroup(Group group) {
    group.addListener(this);
    group.forEach(user -> user.addListener(this));
    group.getJobShiftList().forEach(shift -> shift.addListener(this));
  }

  @Override public void propertyChange(PropertyChangeEvent evt) {
    if (evt.getSource() instanceof Group) {
      System.out.println("The group " + evt.getSource() + " was modified.");
    } else if (evt.getSource() instanceof User) {
      System.out.println("The user " + evt.getSource() + " was modified.");
    } else if (evt.getSource() instanceof JobShift) {
      System.out.println("The jobshift " + evt.getSource() + " was modified.");
    }
  }
}
