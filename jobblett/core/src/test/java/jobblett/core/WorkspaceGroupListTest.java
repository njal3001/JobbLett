package jobblett.core;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import java.util.Iterator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WorkspaceGroupListTest {

  private Workspace workspace;

  private User user;

  @BeforeEach
  public void setUp() {
    workspace = new Workspace();
    user = new User("User1", new HashedPassword("Test12345"), "Ole", "Dole");
  }

  @Test
  public void testNewGroup() {
    Group group = workspace.getGroupList().newGroup("Test");
    assertTrue(group instanceof WorkspaceGroup);
  }

  @Test
  public void testAdd_validGroup() {
    workspace.getUserList().add(user);
    Group group = new Group("Test", 1500);
    group.addUser(user);
    workspace.getGroupList().add(group);
    Iterator iter = workspace.getGroupList().iterator();
    assertTrue(iter.hasNext());
    assertTrue(iter.next() instanceof WorkspaceGroup);
  }

  @Test
  public void testAdd_groupUserNotInUserList() {
    Group group = new Group("Test", 1500);
    group.addUser(user);
    try {
      workspace.getGroupList().add(group);
      fail("Exception should be thrown");      
    } catch (Exception e) {
      assertTrue(e instanceof IllegalArgumentException);
    }
  }
}