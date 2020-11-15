package jobblett.json;

import jobblett.core.Group;
import jobblett.core.HashedPassword;
import jobblett.core.User;
import jobblett.core.Workspace;

public class WorkspacePersistenceTest extends AbstractPersistenceTest<Workspace> {
 
  public WorkspacePersistenceTest() {
    super(Workspace.class);
  }

  @Override
  public Workspace getObject() {
    Workspace workspace = new Workspace();
    User user1 =
        new User("Ola1424", new HashedPassword("Godmorgen1234"), "Ola", "Nordmann");
    User user2 =
        new User("Per2434", new HashedPassword("Godkveld1234"), "Per", "Gudmunsen");
    User user3 = new User("Herman3434", new HashedPassword("Godettermiddag1234"), "Herman",
        "Hermansen");
    workspace.getUserList().add(user1, user2, user3);

    Group group1 = workspace.getGroupList().newGroup("TestGroup1");
    group1.addUser(user1);
    group1.addUser(user2);

    Group group2 = workspace.getGroupList().newGroup("TestGroup2");
    group2.addUser(user3);

    return workspace;
  }

  @Override
  public boolean isEquals(Workspace o1, Workspace o2) {
    UserListPersistenceTest userListPersistenceTest = new UserListPersistenceTest();
    GroupListPersistenceTest groupListPersistenceTest = new GroupListPersistenceTest();

    if (!userListPersistenceTest.isEquals(o1.getUserList(), o2.getUserList())) {
      return false;
    }
    if (!groupListPersistenceTest.isEquals(o2.getGroupList(), o2.getGroupList())) {
      return false;
    }
    return true;
  }

}