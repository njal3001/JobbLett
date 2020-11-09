package jobblett.json;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jobblett.core.Group;
import jobblett.core.GroupList;
import jobblett.core.HashedPassword;
import jobblett.core.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS) public class GroupListPersistenceTest extends AbstractPersistenceTest{

  GroupList groupList = new GroupList();

  public GroupListPersistenceTest() {
    super(GroupList.class);
  }


  @BeforeAll public void setUp() {
    User olav =
        new User("olav", new HashedPassword("bestePassord123"), "Olav", "Nordmann");
    User nora =
        new User("nora", new HashedPassword("bestePassord123"), "Nora", "Bekkestad");
    User petter =
        new User("petter", new HashedPassword("bestePassord123"), "Petter", "Petterson");
    User david = new User("david", new HashedPassword("bestePassord123"), "David", "Berg");

    Group group1 = groupList.newGroup("TestGroup1");
    group1.addUser(olav);
    group1.addUser(nora);

    Group group2 = groupList.newGroup("TestGroup2");
    group2.addUser(petter);
    group2.addUser(david);
  }

  @Override public Object getObject() {
    return groupList;
  }

}
