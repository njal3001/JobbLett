package jobblett.restserver;

import com.fasterxml.jackson.core.JsonProcessingException;
import jobblett.core.Group;
import jobblett.core.GroupList;
import jobblett.core.User;
import jobblett.json.JobblettPersistence;
import jobblett.restapi.WorkspaceService;
import org.junit.jupiter.api.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

public class GroupListResourceTest extends AbstractRestApiTest {

  @Test public void testReplaceGroup() {
    Group group = new Group("Gruppe8", 6803);
    boolean done = post(Boolean.class, "grouplist/replaceGroup", new JobblettPersistence().writeValueAsString(group));
    assertTrue(done);

    Group group1 = get(Group.class, "grouplist/get/6803");
    assertEquals(group.getGroupName(), group1.getGroupName());
  }

  @Test public void testExist() {
    boolean exist = get(Boolean.class, "grouplist/exist/6803");
    assertTrue(exist);
  }

  @Test public void testGetGroups() {
    GroupList groupList = post(GroupList.class, "grouplist/getFromUsers", "olav");
    Group group = groupList.get(6803);
    assertNotNull(group);
  }

  @Test public void getGroupListTest() {
    GroupList groupList = get(GroupList.class, "grouplist");
    Iterator<Group> iterator = groupList.iterator();
    assertTrue(iterator.hasNext());
    Group group = iterator.next();
    assertEquals("Gruppe7", group.getGroupName());
    assertEquals(6803, group.getGroupId());
  }

  @Test public void newGroupTest() {
    Group group = post(Group.class, "grouplist/new", "testerGroup");
    GroupList groupList = get(GroupList.class, "grouplist");
    Group group1 = groupList.get(group.getGroupId());
    assertNotNull(group1);
    assertEquals(group.getGroupName(), group1.getGroupName());
    assertEquals(group.getGroupSize(), group1.getGroupSize());
  }


}
