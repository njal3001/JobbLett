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
import jobblett.core.Group;
import jobblett.core.GroupList;
import jobblett.core.HashedPassword;
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
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
    String responseObjectBody = null;
    try {
      HttpResponse<String> responseObject =
          HttpClient.newBuilder().build().send(requestObject, HttpResponse.BodyHandlers.ofString());
      responseObjectBody = responseObject.body();

    } catch (IOException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return responseObjectBody;
  }

  private <T> T getFromServer(Class<T> t, String url) {
    String responseObjectBody = getBodyFromServer(url);
    T o = null;
    o = new JobblettPersistence().readValue(t, responseObjectBody);
    return o;
  }

  @Override public Group newGroup(String groupName) {
    return getFromServer(Group.class,
        JOBBLETT_SERVICE_PATH + "/" + GROUP_LIST_SERVICE_PATH + "/new/" + groupName);
  }

  @Override public void add(User user) {
    String userString = new JobblettPersistence().writeValueAsString(user);
    getBodyFromServer(JOBBLETT_SERVICE_PATH + "/" + USER_LIST_SERVICE_PATH + "/add/" + userString);
  }

  @Override public Group getGroup(int groupId) {
    return getFromServer(Group.class,
        JOBBLETT_SERVICE_PATH + "/" + GROUP_LIST_SERVICE_PATH + "get/" + groupId);
  }

  @Override public User login(String userName, HashedPassword password) {
    Collection<String> userNameAndPassword = new ArrayList<>();
    userNameAndPassword.add(userName);
    userNameAndPassword.add(password.toString());
    String userNameAndPasswordString =
        new JobblettPersistence().writeValueAsString(userNameAndPassword);
    return getFromServer(User.class,
        JOBBLETT_SERVICE_PATH + "/" + USER_LIST_SERVICE_PATH + "/login/"
            + userNameAndPasswordString);
  }

  @Override public Collection<Group> getGroups(User user) {
    String userString = new JobblettPersistence().writeValueAsString(user);
    return getFromServer(ArrayList.class, userString);
  }

  @Override public void setLists(UserList userList, GroupList groupList) {
    Collection<JobblettList> userListAndGroupList = new ArrayList<>();
    userListAndGroupList.add(userList);
    userListAndGroupList.add(groupList);
    String userListAndGroupListString =
        new JobblettPersistence().writeValueAsString(userListAndGroupList);
    getBodyFromServer(JOBBLETT_SERVICE_PATH + "/setlists/" + userListAndGroupListString);
  }

  @Override public void propertyChange(PropertyChangeEvent evt) {
    // TODO: Skriv hva som skal skje når ting endres uten metodene her
  }
}
