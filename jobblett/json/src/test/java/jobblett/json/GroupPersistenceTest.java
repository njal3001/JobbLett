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
import org.junit.jupiter.api.BeforeEach;

public class GroupPersistenceTest extends AbstractPersistenceTest<Group> {

  Group group = new Group("TestGroup", ThreadLocalRandom.current().nextInt(1000, 10000));

  public GroupPersistenceTest() {
    super(Group.class);
  }


  @BeforeEach public void setUp() {
    User olav =
        new User("olav", new HashedPassword("bestePassord123"), "Olav", "Nordmann");
    User nora =
        new User("nora", new HashedPassword("bestePassord123"), "Nora", "Bekkestad");
    //TODO: IKKE BRUKT?
    User petter =
        new User("petter", new HashedPassword("bestePassord123"), "Petter", "Petterson");
    User david = new User("david", new HashedPassword("bestePassord123"), "David", "Berg");

    group.addUser(olav);
    group.addUser(nora);
  }

  @Override public Group getObject() {
    return group;
  }


  @Override
  public boolean isEquals(Group o1, Group o2) {
    if (o1.getGroupSize() != o2.getGroupSize()) {
      return false;
    }
     for (User user : o1) {
        if (o2.getUser(user.getUsername()) == null) {
          return false;
        }
      }
      return true;
    }
  }
