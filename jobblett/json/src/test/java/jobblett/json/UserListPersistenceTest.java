package jobblett.json;

import static org.junit.jupiter.api.Assertions.fail;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jobblett.core.HashedPassword;
import jobblett.core.User;
import jobblett.core.UserList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

public class UserListPersistenceTest extends AbstractPersistenceTest<UserList>{

  private UserList userList = new UserList();

  public UserListPersistenceTest() {
    super(UserList.class);
  }


  @BeforeEach public void setUp() {
    User user1 =
        new User("Ola1424", new HashedPassword("Godmorgen1234"), "Ola", "Nordmann");
    User user2 =
        new User("Per2434", new HashedPassword("Godkveld1234"), "Per", "Gudmunsen");
    User user3 = new User("Herman3434", new HashedPassword("Godettermiddag1234"), "Herman",
        "Hermansen");
    userList.add(user1, user2, user3);
  }

  @Override public UserList getObject() {
    return userList;
  }
}
