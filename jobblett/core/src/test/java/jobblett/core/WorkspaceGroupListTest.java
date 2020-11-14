package jobblett.core;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;

public class WorkspaceGroupListTest {

  private Workspace workspace;

  @BeforeEach
  public void setUp() {
    workspace = new Workspace();
  }

  public void testNewGroup() {
    Group group = workspace.getGroupList().newGroup("Test");
    assertTrue(group instanceof WorkspaceGroup);
  }
  
}