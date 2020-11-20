package jobblett.restserver;

import com.fasterxml.jackson.core.JsonProcessingException;
import jobblett.core.JobShift;
import jobblett.restapi.WorkspaceService;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static jobblett.restapi.JobShiftListResource.JOB_SHIFT_LIST_RESOURCE_PATH;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JobShiftResourceTest extends AbstractRestApiTest {
  @Test public void testGetJobshiftResource() {
      JobShift jobShift = get(JobShift.class, "grouplist/get/6803/" + JOB_SHIFT_LIST_RESOURCE_PATH + "/get/0");
      //assertEquals("7200", jobShift.getDuration().toSecondsPart());
      assertEquals("2021-10-15T17:44:04.738", jobShift.getStartingTime().toString());
      assertEquals("Dette er Olav sin skift.", jobShift.getInfo());
  }
}
