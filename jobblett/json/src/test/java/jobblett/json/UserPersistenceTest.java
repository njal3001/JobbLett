package jobblett.json;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jobblett.core.HashedPassword;
import jobblett.core.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS) public class UserPersistenceTest extends AbstractPersistenceTest {

  User user =
      new User("Olavh123", HashedPassword.hashPassword("Heisann123456"), "Olav", "Hermansen");

  public UserPersistenceTest() {
    super(User.class);
  }

  @Override public Object getObject() {
    return user;
  }


  /*@Test public void persistenceTest() {

    // Serializing
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
    mapper.registerModule(new JobblettCoreModule());
    String result = "";

    try {
      result = mapper.writeValueAsString(user);

    } catch (JsonProcessingException e) {
      e.printStackTrace();
      fail(e);
    }

    // Deserializing
    mapper = new ObjectMapper();
    mapper.registerModule(new JobblettCoreModule());

    try {
      User newUser = mapper.readValue(result, User.class);
      assertTrue(newUser.equals(user));
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      fail(e);
    }

  }

  public static void main(String[] args) {
    UserPersistenceTest test = new UserPersistenceTest();
    test.persistenceTest();
  }

  */
}
