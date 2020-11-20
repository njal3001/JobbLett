package jobblett.restserver;

import com.fasterxml.jackson.core.JsonProcessingException;
import jobblett.core.HashedPassword;
import jobblett.core.JobShift;
import jobblett.core.JobShiftList;
import jobblett.core.User;
import jobblett.core.UserList;
import jobblett.json.JobblettPersistence;
import jobblett.restapi.WorkspaceService;
import org.junit.jupiter.api.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Iterator;

import static jobblett.restapi.JobShiftListResource.JOB_SHIFT_LIST_RESOURCE_PATH;
import static org.junit.jupiter.api.Assertions.*;

public class UserListResourceTest extends AbstractRestApiTest {
  @Test public void getUserListTest() {
    UserList userList = get(UserList.class, "userlist");
    Iterator<User> iterator = userList.iterator();
    assertTrue(iterator.hasNext());
    User user1 = iterator.next();
    assertTrue(iterator.hasNext());
    User user2 = iterator.next();
    assertTrue(iterator.hasNext());
    User user3 = iterator.next();
    assertTrue(iterator.hasNext());
    User user4 = iterator.next();

    assertUser(user1, "olav", "Olav", "Nordmann");
    assertUser(user2, "nora", "Nora", "Bekkestad");
    assertUser(user3, "petter", "Petter", "Petterson");
    assertUser(user4, "david", "David", "Berg");
  }

  @Test public void testGetUser() {
    User user = get(User.class, "userlist/get/olav");
    assertUser(user, "olav", "Olav", "Nordmann");
  }

  @Test
  public void testAddUser(){
    User user = new User("newUser", new HashedPassword("bestePassord123"), "NewUser", "LastName");
    put("userlist/add", jobblettPersistence.writeValueAsString(user));
  }



}
