package jobblett.ui.remote;

import jobblett.core.Workspace;
import jobblett.json.JobblettPersistence;
import jobblett.restserver.JobblettConfig;

public class TestJobblettConfig extends JobblettConfig {

  public TestJobblettConfig(Workspace workspace) {
    super(workspace);
  }

  private static Workspace createTestWorkspace() {
    Workspace workspace = new Workspace();

    return new JobblettPersistence().readDefault(Workspace.class);
  }
}
