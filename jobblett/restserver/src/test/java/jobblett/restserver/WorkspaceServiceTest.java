package jobblett.restserver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jobblett.core.*;
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
import org.junit.jupiter.api.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Iterator;

import static jobblett.restapi.JobShiftListResource.JOB_SHIFT_LIST_RESOURCE_PATH;
import static jobblett.restapi.UserListResource.USER_LIST_RESCORCE_PATH;
import static org.junit.jupiter.api.Assertions.*;


public class WorkspaceServiceTest extends AbstractRestApiTest {
  @Test public void getWorkspace() {
    Response getResponse = target(WorkspaceService.WORKSPACE_SERVICE_PATH)
        .request().accept(MediaType.APPLICATION_JSON).get();
    assertEquals(200, getResponse.getStatus());
  }
}
  

    
