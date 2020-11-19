package jobblett.restserver;


import jobblett.core.Workspace;
import jobblett.json.JobblettPersistence;
import jobblett.restapi.WorkspaceService;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

public class JobblettConfig extends ResourceConfig {
  private Workspace workspace;

  protected JobblettConfig(Workspace workspace) {
    setWorkspace(workspace);
    register(WorkspaceService.class);
    register(JobblettModuleObjectMapperProvider.class);
    register(JacksonFeature.class);
    register(new AbstractBinder() {
      @Override protected void configure() {
        bind(JobblettConfig.this.workspace);
      }
    });

  }

  public JobblettConfig() {
    this(createDefaultWorkspace());
  }

  public void setWorkspace(Workspace workspace) {
    this.workspace = workspace;
  }

  private static Workspace createDefaultWorkspace() {
    return new JobblettPersistence().readDefault(Workspace.class);
  }
}
