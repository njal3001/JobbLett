package jobblett.restserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import jobblett.core.User;
import jobblett.core.Workspace;
import jobblett.json.JobblettPersistence;
import jobblett.restapi.WorkspaceService;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.glassfish.jersey.test.grizzly.GrizzlyTestContainerFactory;
import org.glassfish.jersey.test.spi.TestContainerException;
import org.glassfish.jersey.test.spi.TestContainerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static jobblett.restapi.JobShiftListResource.JOB_SHIFT_LIST_RESOURCE_PATH;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AbstractRestApiTest extends JerseyTest {

  protected Workspace workspace;
  protected JobblettPersistence jobblettPersistence = new JobblettPersistence();

  protected boolean shouldLog() {
    return false;
  }

  @Override protected ResourceConfig configure() {
    workspace = new JobblettPersistence().readDefault(Workspace.class);
    final JobblettConfig jobblettConfig = new JobblettConfig(workspace);
    if (shouldLog()) {
      enable(TestProperties.LOG_TRAFFIC);
      enable(TestProperties.DUMP_ENTITY);
      jobblettConfig.property(LoggingFeature.LOGGING_FEATURE_LOGGER_LEVEL_SERVER, "WARNING");
    }
    return jobblettConfig;
  }

  @Override @BeforeEach public void setUp() throws Exception {
    super.setUp();
  }

  @Override @AfterEach public void tearDown() throws Exception {
    super.tearDown();
  }

  @Override protected TestContainerFactory getTestContainerFactory() throws TestContainerException {
    return new GrizzlyTestContainerFactory();
  }

  protected <T> T get(Class<T> tClass, String url) {
    Response getResponse = target(WorkspaceService.WORKSPACE_SERVICE_PATH)
        .path(url)
        .request(MediaType.APPLICATION_JSON + ";" + MediaType.CHARSET_PARAMETER + "=UTF8")
        .get();
    assertEquals(200, getResponse.getStatus());
    return new JobblettPersistence().readValue(tClass, getResponse.readEntity(String.class));
  }

  protected <T> T post(Class<T> tClass, String url, String requestBody) {
    Response getResponse = target(WorkspaceService.WORKSPACE_SERVICE_PATH)
        .path(url)
        .request()
        .accept(MediaType.APPLICATION_JSON)
        .post(Entity.json(requestBody));
    assertEquals(200, getResponse.getStatus());
    return new JobblettPersistence().readValue(tClass, getResponse.readEntity(String.class));
  }

  protected void put(String url, String requestBody) {
    Response getResponse = target(WorkspaceService.WORKSPACE_SERVICE_PATH)
        .path(url)
        .request(MediaType.APPLICATION_JSON + ";" + MediaType.CHARSET_PARAMETER + "=UTF8")
        .put(Entity.json(requestBody));
    assertEquals(204, getResponse.getStatus());
  }

  protected void assertUser(User user, String username, String givenName, String familyName) {
    assertEquals(user.getUsername(), username);
    assertEquals(user.getGivenName(), givenName);
    assertEquals(user.getFamilyName(), familyName);
  }

}
