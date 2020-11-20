package jobblett.json;

import java.util.Iterator;
import jobblett.core.HashedPassword;
import jobblett.core.User;
import jobblett.core.UserList;
import org.junit.jupiter.api.BeforeEach;


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


  @Override
  public boolean isEquals(UserList o1, UserList o2) {
    if (o1.size() != o2.size()) {
      return false;
    }

    Iterator<User> iter1 = o1.iterator();
    Iterator<User> iter2 = o2.iterator();
    UserPersistenceTest userPersistenceTest = new UserPersistenceTest();
    
    while (iter1.hasNext()) {
      if(!userPersistenceTest.isEquals(iter1.next(), iter2.next())) {
        return false;
      }
    }
    return true;
  }
}
