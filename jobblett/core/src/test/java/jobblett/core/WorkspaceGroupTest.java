package jobblett.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WorkspaceGroupTest {

  private Workspace workspace;
  private WorkspaceGroup group;
  private User user;

  @BeforeEach
  public void setUp() {
    workspace = new Workspace();
    group = new WorkspaceGroup("test", 5000, workspace);
    user = new User("test1", new HashedPassword("Passord123"), "Kari", "Testermann");
  }

  @Test
  public void testAddUser_notInUserList() {
    try {
      group.addUser(user);
      fail("Exception should be thrown");
    } catch(Exception e) {
      assertTrue(e instanceof IllegalArgumentException);
    }
  }

  @Test
  public void testAddUser_inUserList() {
    workspace.getUserList().add(user);
    group.addUser(user);
    System.out.println(group.getGroupSize());
    assertEquals(user, group.getUser(user.getUsername()));
  }
}