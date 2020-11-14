package jobblett.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WorkspaceUserListTest {

  private Workspace workspace;
  private User user;

  @BeforeEach
  public void setUp() {
    workspace = new Workspace();
    user = new User("test1", new HashedPassword("Passord123"), "Kari", "Testermann");
  }
  
  @Test
  public void testRemoveUser() {
    workspace.getUserList().add(user);
    Group group = workspace.getGroupList().newGroup("Test");
    group.addUser(user);
    workspace.getUserList().removeUser(user);
    assertEquals(null, group.getUser(user.getUsername()));
  }
}