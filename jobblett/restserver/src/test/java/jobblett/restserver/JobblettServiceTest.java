package jobblett.restserver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jobblett.core.Group;
import jobblett.core.GroupList;
import jobblett.core.User;
import jobblett.core.UserList;
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

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;


public class JobblettServiceTest extends JerseyTest{

  protected boolean shouldLog(){
    return false;
  }


  @Override
  protected ResourceConfig configure(){
    final JobblettConfig jobblettConfig = new JobblettConfig();
    if(shouldLog()){
      enable(TestProperties.LOG_TRAFFIC);
      enable(TestProperties.DUMP_ENTITY);
      jobblettConfig.property(LoggingFeature.LOGGING_FEATURE_LOGGER_LEVEL_SERVER, "WARNING");
    }
    return jobblettConfig;
  }

  @Override
  protected TestContainerFactory getTestContainerFactory() throws TestContainerException {
    return new GrizzlyTestContainerFactory();
  }

  private ObjectMapper objectMapper;

  @Override
  @BeforeEach
  public void setUp() throws Exception{
    super.setUp();
    objectMapper = new JobblettModuleObjectMapperProvider().getContext(getClass());
    System.out.println(objectMapper);
  }

  @Override
  @AfterEach
  public void tearDown() throws Exception{
    super.tearDown();
  }

  private void assertUser(User user, String username, String givenName, String familyName){
    assertEquals(user.getUsername(), username);
    assertEquals(user.getGivenName(), givenName);
    assertEquals(user.getFamilyName(), familyName);
  }

  @Test
  public void UserListTest(){
    Response getResponse = target(WorkspaceService.WORKSPACE_SERVICE_PATH).path("userlist")
        .request(MediaType.APPLICATION_JSON + ";" + MediaType.CHARSET_PARAMETER+"=UTF8")
        .get();
    assertEquals(200, getResponse.getStatus());
    try{
      UserList userList = objectMapper.readValue(getResponse.readEntity(String.class), UserList.class);
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

    } catch (JsonProcessingException e){
      fail(e.getMessage());
    }
  }

  @Test
  public void GroupListTest(){
    Response getResponse = target(WorkspaceService.WORKSPACE_SERVICE_PATH).path("grouplist")
        .request(MediaType.APPLICATION_JSON + ";" + MediaType.CHARSET_PARAMETER+"=UTF8")
        .get();
    assertEquals(200, getResponse.getStatus());
    try{
      GroupList groupList = objectMapper.readValue(getResponse.readEntity(String.class),GroupList.class);
      Iterator<Group> iterator = groupList.iterator();
      assertTrue(iterator.hasNext());
      Group group = iterator.next();
      assertEquals("Gruppe7",group.getGroupName());
      assertEquals(6803, group.getGroupId());


    } catch (JsonProcessingException e){

    }
  }




}
