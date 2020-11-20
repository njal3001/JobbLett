package jobblett.ui;


import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Iterator;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import jobblett.core.Group;
import jobblett.core.GroupList;
import jobblett.core.HashedPassword;
import jobblett.core.JobShift;
import jobblett.core.User;
import jobblett.json.JobblettPersistence;

public class RemoteWorkspaceAccessTest {
  
  private WireMockConfiguration config;
  private WireMockServer wireMockServer;

  private RemoteWorkspaceAccess workspaceAccess;
  private JobblettPersistence jobblettPersistence;

  User user1, user2;
  Group group1, group2;
  GroupList groupList;
  JobShift jobShift1, jobShift2;
  
  @BeforeEach
  public void startWireMockServerAndSetup() throws URISyntaxException {
    config = WireMockConfiguration.wireMockConfig().port(8089);
    wireMockServer = new WireMockServer(config.portNumber());
    wireMockServer.start();
    WireMock.configureFor("localhost", config.portNumber());
    workspaceAccess = new RemoteWorkspaceAccess(new URI("http://localhost:" + wireMockServer.port() + "/jobblett"));
    jobblettPersistence = new JobblettPersistence();

    user1 = new User("user1", new HashedPassword("Test12345"), "Ole", "Dole");
    user2 = new User("user2", new HashedPassword("Test12345"), "Ole", "Dole");
    group1 = new Group("Test group1", 1500);
    group2 = new Group("Test group2", 2000);
    groupList = new GroupList();
    groupList.add(group1, group2);
    jobShift1 = new JobShift(user1, LocalDateTime.now().minusHours(2), Duration.ofHours(2), "Test info");
    jobShift2 = new JobShift(user2, LocalDateTime.now().plusHours(2), Duration.ofHours(2), "Test info 2");
  }

  private void getUserStub(User user) {
    String userString = jobblettPersistence.writeValueAsString(user1);
    stubFor(get("/userlist/get/user1")
        .withHeader("Accept", equalTo("application/json"))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withBody(userString)
        )
    );
  }

  private void hasUserStub() {
    stubFor(post(urlEqualTo("/userlist/exist"))
        .withHeader("Accept", equalTo("application/json"))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withBody("true")
        )
    );
  }

  @Test
  public void testUser() {
    hasUserStub();
    getUserStub(user1);
    assertTrue(workspaceAccess.hasUser(user1.getUsername()));
    assertEquals(user1.getGivenName() + " " + user1.getFamilyName(), workspaceAccess.getUserFullName(user1.getUsername()));
    assertEquals(user1.toString(), workspaceAccess.getUserToString(user1.getUsername()));
    assertTrue(workspaceAccess.correctPassword(user1.getUsername(), "Test12345"));
  }

  private void getGroupStub(Group group) {
    String groupString = jobblettPersistence.writeValueAsString(group1);
    stubFor(get(urlEqualTo("/grouplist/get/1500"))
        .withHeader("Accept", equalTo("application/json"))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withBody(groupString)
        )
    );
  }

  private void hasGroupStub() {
    stubFor(get(urlEqualTo("/grouplist/exist/1500"))
        .withHeader("Accept", equalTo("application/json"))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withBody("true")
        )
    );
  }

  @Test
  public void testGroup() {
    group1.addUser(user1);
    group1.addUser(user2);
    getGroupStub(group1);
    hasGroupStub();

    assertTrue(workspaceAccess.hasGroup(group1.getGroupId()));
    assertEquals(group1.getGroupName(), workspaceAccess.getGroupName(group1.getGroupId()));
    Iterator<String> iter = workspaceAccess.getGroupUsernames(group1.getGroupId()).iterator();
    assertTrue(iter.hasNext());
    assertEquals(user1.getUsername(), iter.next());
    assertTrue(iter.hasNext());
    assertEquals(user2.getUsername(), iter.next());
    assertFalse(iter.hasNext());
  }

    @Test
    public void testGetAllGroupIds() {
      group1.addUser(user1);
      group2.addUser(user1);
      String groupListString = jobblettPersistence.writeValueAsString(groupList);
      stubFor(post(urlEqualTo("/grouplist/getFromUsers"))
        .withHeader("Accept", equalTo("application/json"))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withBody(groupListString)
        )
    );
      Collection<Integer> ids = workspaceAccess.getAllGroupIds(user1.getUsername());
      assertTrue(ids.contains(group1.getGroupId()));
      assertTrue(ids.contains(group2.getGroupId()));
    }

    @Test
    public void testIsGroupAdmin() {
      stubFor(get(urlEqualTo("/grouplist/get/1500/isAdmin/user1"))
        .withHeader("Accept", equalTo("application/json"))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withBody("true")
        )
    );     
      assertTrue(workspaceAccess.isGroupAdmin(group1.getGroupId(), user1.getUsername()));
    }

    @Test
    public void testGetJobShift() {
      String jobShiftString = jobblettPersistence.writeValueAsString(jobShift1);
      stubFor(get(urlEqualTo("/grouplist/get/1500/shifts/get/0"))
        .withHeader("Accept", equalTo("application/json"))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withBody(jobShiftString)
        )
        );  
      assertEquals(workspaceAccess.getJobShiftUsername(group1.getGroupId(), 0), jobShift1.getUser().getUsername());
      assertEquals(workspaceAccess.getJobShiftStartingTime(group1.getGroupId(), 0), jobShift1.getStartingTime());
      assertEquals(workspaceAccess.getJobShiftEndingTime(group1.getGroupId(), 0), jobShift1.getEndingTime());
      assertEquals(workspaceAccess.getJobShiftInfo(group1.getGroupId(), 0), jobShift1.getInfo());
      assertTrue(workspaceAccess.jobShiftIsOutdated(group1.getGroupId(), 0));
    }

    @Test
    public void testGetJobShiftIndexes() {
      group1.addUser(user1);
      group1.addUser(user2);
      group1.addAdmin(user1);
      group1.addJobShift(jobShift1, user1);
      group1.addJobShift(jobShift2, user1);
      hasGroupStub();
      getGroupStub(group1);
      getUserStub(user1);

      Iterator<Integer> iter = workspaceAccess.getJobShiftIndexes(group1.getGroupId(), user2.getUsername()).iterator();
      assertTrue(iter.hasNext());
      assertEquals(1, iter.next());
      assertFalse(iter.hasNext());
    }

  @AfterEach
  public void stopWireMockServer() {
    wireMockServer.stop();
  }
}