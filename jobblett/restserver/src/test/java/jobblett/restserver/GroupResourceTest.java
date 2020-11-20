package jobblett.restserver;

import com.fasterxml.jackson.core.JsonProcessingException;
import jobblett.core.Group;
import jobblett.restapi.WorkspaceService;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GroupResourceTest extends AbstractRestApiTest {
  @Test public void getGroupIDtest() {
      Group group = get(Group.class, "grouplist/get/6803");
      assertEquals(group.getGroupId(), 6803);
  }
}
