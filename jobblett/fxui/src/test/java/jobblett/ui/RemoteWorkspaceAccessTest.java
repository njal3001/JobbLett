package jobblett.ui;


import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.net.URI;
import java.net.URISyntaxException;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import jobblett.core.Group;
import jobblett.core.HashedPassword;
import jobblett.core.User;
import jobblett.json.JobblettPersistence;

public class RemoteWorkspaceAccessTest {
  
  private WireMockConfiguration config;
  private WireMockServer wireMockServer;

  private RemoteWorkspaceAccess workspaceAccess;
  private JobblettPersistence jobblettPersistence;

  User user1;
  Group group1;
  
  @BeforeEach
  public void startWireMockServerAndSetup() throws URISyntaxException {
    config = WireMockConfiguration.wireMockConfig().port(8089);
    wireMockServer = new WireMockServer(config.portNumber());
    wireMockServer.start();
    WireMock.configureFor("localhost", config.portNumber());
    workspaceAccess = new RemoteWorkspaceAccess(new URI("http://localhost:" + wireMockServer.port() + "/jobblett"));
    jobblettPersistence = new JobblettPersistence();

    user1 = new User("user1", new HashedPassword("Test12345"), "Ole", "Dole");
    group1 = new Group("Test group", 1500);
  }

  @Test
  public void testUser() {
    String userString = jobblettPersistence.writeValueAsString(user1);
    stubFor(get("/jobblettt/userlist/get/user1")
        .withHeader("Accept", equalTo("application/json"))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withBody(userString)
        )
    );

    stubFor(post(urlEqualTo("/jobblett/userlist/exist/"))
        .withHeader("Accept", equalTo("application/json"))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withBody("true")
        )
    );

    assertTrue(workspaceAccess.hasUser(user1.getUsername()));
    assertEquals(user1.getGivenName() + " " + user1.getFamilyName(), workspaceAccess.getUserFullName(user1.getUsername()));
    assertEquals(user1.toString(), workspaceAccess.getUserToString(user1.getUsername()));
    assertTrue(workspaceAccess.correctPassword(user1.getUsername(), "Test12345"));
  }

  @Test
  public void testGroup() {
    String groupString = jobblettPersistence.writeValueAsString(group1);
    stubFor(get(urlEqualTo("/jobblett/grouplist/get/1500"))
        .withHeader("Accept", equalTo("application/json"))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withBody(groupString)
        )
    );

    //assertTrue(workspaceAccess.hasGroup(group1.getGroupId()));
    //assertEquals(group1.getGroupName(), workspaceAccess.getGroupName(group1.getGroupId()));
  }


  @AfterEach
  public void stopWireMockServer() {
    wireMockServer.stop();
  }
}