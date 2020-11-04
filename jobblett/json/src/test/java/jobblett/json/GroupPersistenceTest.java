package jobblett.json;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.util.concurrent.ThreadLocalRandom;
import jobblett.core.Group;
import jobblett.core.HashedPassword;
import jobblett.core.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS) public class GroupPersistenceTest {

  Group group = new Group("TestGroup", ThreadLocalRandom.current().nextInt(1000, 10000));

  public static void main(String[] args) {
    GroupPersistenceTest test = new GroupPersistenceTest();
    test.setUp();
    test.persistenceTest();
  }

  @BeforeAll public void setUp() {
    User olav =
        new User("olav", HashedPassword.hashPassword("bestePassord123"), "Olav", "Nordmann");
    User nora =
        new User("nora", HashedPassword.hashPassword("bestePassord123"), "Nora", "Bekkestad");
    User petter =
        new User("petter", HashedPassword.hashPassword("bestePassord123"), "Petter", "Petterson");
    User david = new User("david", HashedPassword.hashPassword("bestePassord123"), "David", "Berg");

    group.addUser(olav);
    group.addUser(nora);
  }

  @Test public void persistenceTest() {

    // Serializing
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
    mapper.registerModule(new JobblettCoreModule());
    String result = "";

    try {
      result = mapper.writeValueAsString(group);

    } catch (JsonProcessingException e) {
      e.printStackTrace();
      fail(e);
    }

    // Deserializing
    mapper = new ObjectMapper();
    mapper.registerModule(new JobblettCoreModule());
    try {
      Group newGroup = mapper.readValue(result, Group.class);
      assertEquals(group, newGroup);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      fail(e);
    }

  }
}
