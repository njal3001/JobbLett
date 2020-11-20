package jobblett.json;

import java.util.Iterator;
import jobblett.core.Group;
import jobblett.core.GroupList;
import jobblett.core.HashedPassword;
import jobblett.core.User;
import org.junit.jupiter.api.BeforeEach;

public class GroupListPersistenceTest extends AbstractPersistenceTest<GroupList> {

  GroupList groupList = new GroupList();

  public GroupListPersistenceTest() {
    super(GroupList.class);
  }


  @BeforeEach
  public void setUp() {
    User olav = new User("olav", new HashedPassword("bestePassord123"), "Olav", "Nordmann");
    User nora = new User("nora", new HashedPassword("bestePassord123"), "Nora", "Bekkestad");
    User petter = new User("petter", new HashedPassword("bestePassord123"), "Petter", "Petterson");
    User david = new User("david", new HashedPassword("bestePassord123"), "David", "Berg");

    Group group1 = groupList.newGroup("TestGroup1");
    group1.addUser(olav);
    group1.addUser(nora);

    Group group2 = groupList.newGroup("TestGroup2");
    group2.addUser(petter);
    group2.addUser(david);
  }

  @Override
  public GroupList getObject() {
    return groupList;
  }


  @Override
  public boolean isEquals(GroupList o1, GroupList o2) {
    if(o1.size() != o2.size()) {
      return false;
    }

    Iterator<Group> iter1 = o1.iterator();
    Iterator<Group> iter2 = o2.iterator();
    GroupPersistenceTest groupPersistenceTest = new GroupPersistenceTest();

    while (iter1.hasNext()) {
      if (!groupPersistenceTest.isEquals(iter1.next(), iter2.next())) {
        return false;
      }
    }
    return true;
  }
}
