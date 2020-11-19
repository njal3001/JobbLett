package jobblett.ui;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import java.net.URI;
import java.net.URISyntaxException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class RemoteWorkspaceAccessTest {

  private WireMockConfiguration config;
  private WireMockServer wireMockServer;

  private RemoteWorkspaceAccess remoteWorkspaceAccess;
  
  @BeforeEach
  public void startWireMockServerAndSetup() throws URISyntaxException {
    config = WireMockConfiguration.wireMockConfig().port(8089);
    wireMockServer = new WireMockServer(config.portNumber());
    wireMockServer.start();
    WireMock.configureFor("localhost", config.portNumber());
    remoteWorkspaceAccess = new RemoteWorkspaceAccess(new URI("http://localhost:" + wireMockServer.port() + "/jobblett"));
  }

  // TODO
  /*@Test
  public void testAddUser() {
    remoteWorkspaceAccess.addUser("TestUser", "Test12345", "Te", "Te");
    assertEquals("Te", remoteWorkspaceAccess.getUserFullName("TestUser"));
  }*/

  // TODO
  /*@Test
  public void testNewGroup() {
    int groupId = remoteWorkspaceAccess.newGroup("TestGroup");
    assertEquals("TestGroup", remoteWorkspaceAccess.getGroupName(groupId));
  }*/

  @AfterEach
  public void stopWireMockServer() {
    wireMockServer.stop();
  }
}
