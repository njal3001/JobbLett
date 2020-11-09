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

@TestInstance(TestInstance.Lifecycle.PER_CLASS) public class GroupPersistenceTest extends AbstractPersistenceTest{

  Group group = new Group("TestGroup", ThreadLocalRandom.current().nextInt(1000, 10000));

  public GroupPersistenceTest() {
    super(Group.class);
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

  @Override public Object getObject() {
    return group;
  }
}
