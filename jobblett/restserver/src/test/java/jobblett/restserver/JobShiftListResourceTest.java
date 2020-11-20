package jobblett.restserver;

import com.fasterxml.jackson.core.JsonProcessingException;
import jobblett.core.HashedPassword;
import jobblett.core.JobShift;
import jobblett.core.JobShiftList;
import jobblett.core.User;
import jobblett.json.JobblettPersistence;
import jobblett.restapi.WorkspaceService;
import org.junit.jupiter.api.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Iterator;

import static jobblett.restapi.JobShiftListResource.JOB_SHIFT_LIST_RESOURCE_PATH;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JobShiftListResourceTest extends AbstractRestApiTest {
  @Test
  public void testAddJobshift(){
    User user = new User("olav", new HashedPassword("bestePassord123"), "Olav", "Nordmann");
    JobShift jobShift =
        new JobShift(user, LocalDateTime.of(2021, 12, 20, 12, 30), Duration.ofHours(3), "tester add jobshift");
    put("grouplist/get/6803/"+JOB_SHIFT_LIST_RESOURCE_PATH+"/add/olav", new JobblettPersistence().writeValueAsString(jobShift));
  }

  @Test public void testGetJobshifts() {
    JobShiftList jobShiftList = get(JobShiftList.class, "grouplist/get/6803/" + JOB_SHIFT_LIST_RESOURCE_PATH);
    Iterator<JobShift> iterator = jobShiftList.iterator();
    assertTrue(iterator.hasNext());
    JobShift jobShift = iterator.next();
    assertEquals("2021-10-15T17:44:04.738", jobShift.getStartingTime().toString());
    assertEquals("Dette er Olav sin skift.", jobShift.getInfo());
  }

  @Test public void testRemoveJobshift() {
    boolean success = get(Boolean.class, "grouplist/get/6803/"+JOB_SHIFT_LIST_RESOURCE_PATH+"/remove/olav/0");
    assertTrue(success);
    JobShiftList jobShiftList = get(JobShiftList.class, "grouplist/get/6803/"+JOB_SHIFT_LIST_RESOURCE_PATH);
    assertEquals(jobShiftList.size(), 0);
  }

  @Test public void deleteOutdatedJobShiftsTest() {
    put("grouplist/get/6803/"+JOB_SHIFT_LIST_RESOURCE_PATH+"/deleteOutdated","");
  }
}
