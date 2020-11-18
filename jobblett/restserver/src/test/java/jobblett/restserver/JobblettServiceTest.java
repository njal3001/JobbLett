package jobblett.restserver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


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
  }

  @Override
  @AfterEach
  public void tearDown() throws Exception{
    super.tearDown();
  }

  @Test
  public void UserListTest(){
    Response getResponse = target(WorkspaceService.WORKSPACE_SERVICE_PATH)
        .request(MediaType.APPLICATION_JSON + ";" + MediaType.CHARSET_PARAMETER+"=UTF8")
        .get();
    //assertEquals(200, getResponse.getStatus());
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

      assertEquals("olav", user1.getUsername());
      assertEquals("Olav", user1.getGivenName());
      assertEquals("Nordmann", user1.getFamilyName());

      assertEquals("nora", user2.getUsername());
      assertEquals("Nora", user2.getGivenName());
      assertEquals("Bekkestad", user2.getFamilyName());

      assertEquals("petter", user3.getUsername());
      assertEquals("Petter", user3.getGivenName());
      assertEquals("Petterson", user3.getFamilyName());

      assertEquals("david", user4.getUsername());
      assertEquals("David", user4.getGivenName());
      assertEquals("Berg", user4.getFamilyName());


    } catch (JsonProcessingException e){
    }
  }


}
