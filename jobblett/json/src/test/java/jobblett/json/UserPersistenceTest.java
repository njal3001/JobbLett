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

public class UserPersistenceTest extends AbstractPersistenceTest<User> {

  User user =
      new User("Olavh123", new HashedPassword("Heisann123456"), "Olav", "Hermansen");

  public UserPersistenceTest() {
    super(User.class);
  }

  @Override public User getObject() {
    return user;
  }
}
