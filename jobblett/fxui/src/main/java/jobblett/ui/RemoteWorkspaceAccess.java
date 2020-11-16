package jobblett.ui;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
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
      requestObject =
          HttpRequest.newBuilder(endpointBaseUri.resolve(new URI(url))).header("Accept", "application/json").build();
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
          HttpRequest.newBuilder(endpointBaseUri.resolve(new URI(urlString))).header("Accept", "application/json")
              .header("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString(body)).build();
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
          HttpRequest.newBuilder(endpointBaseUri.resolve(new URI(urlString))).header("Accept", "application/json")
              .header("Content-Type", "application/json").PUT(HttpRequest.BodyPublishers.ofString(body)).build();
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


  @Override
  public void addUser(String username, String password, String givenName, String familyName) {
    // TODO Auto-generated method stub

  }

  @Override
  public boolean hasUser(String username) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean correctPassword(String username, String password) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public String getUserFullName(String username) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getUserToString(String username) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public int newGroup(String groupName) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public boolean hasGroup(int groupId) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public String getGroupName(int groupId) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Collection<Integer> getAllGroupIds(String username) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Collection<String> getGroupUsernames(int groupId) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void addGroupUser(int groupId, String username) {
    // TODO Auto-generated method stub

  }

  @Override
  public boolean hasGroupUser(int groupId, String username) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public void addGroupAdmin(int groupId, String username) {
    // TODO Auto-generated method stub

  }

  @Override
  public boolean isGroupAdmin(int groupId, String username) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public void updateJobShift(int groupId, int index, String username, LocalDateTime startingTime, Duration duration,
      String info) {
    // TODO Auto-generated method stub

  }

  @Override
  public void deleteJobShift(int groupId, int index) {
    // TODO Auto-generated method stub

  }

  @Override
  public void addJobShift(String username, int groupId, String jobShiftUsername, LocalDateTime startingTime,
      Duration duration, String info) {
    // TODO Auto-generated method stub

  }

  @Override
  public int getJobShiftsSize(int groupId) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public List<Integer> getJobShiftIndexes(int groupId, String username) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getJobShiftUsername(int groupId, int index) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public LocalDateTime getJobShiftStartingTime(int groupId, int index) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public LocalDateTime getJobShiftEndingTime(int groupId, int index) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getJobShiftInfo(int groupId, int index) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean jobShiftIsOutdated(int groupId, int index) {
    // TODO Auto-generated method stub
    return false;
  }
}
