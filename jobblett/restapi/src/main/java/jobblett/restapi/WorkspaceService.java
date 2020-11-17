package jobblett.restapi;

import static jobblett.restapi.GroupListResource.GROUP_LIST_RESCORCE_PATH;
import static jobblett.restapi.UserListResource.USER_LIST_RESCORCE_PATH;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import jobblett.core.Workspace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Path(WorkspaceService.WORKSPACE_SERVICE_PATH)
public class WorkspaceService extends RestApiClass {
  public static final String WORKSPACE_SERVICE_PATH = "jobblett";
  protected static final Logger LOG = LoggerFactory.getLogger(WorkspaceService.class);


  @Inject 
  private Workspace workspace;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Workspace getWorkspace() {
    return workspace;
  }

  @Path("/" + GROUP_LIST_RESCORCE_PATH)
  public GroupListResource getGroupListResource() {
    debug("Sub-resource for GroupList");
    return new GroupListResource(workspace.getGroupList());
  }

  @Path("/" + USER_LIST_RESCORCE_PATH)
  public UserListResource getUserListResource() {
    debug("Sub-resource for UserList");
    return new UserListResource(workspace.getUserList());
  }

  @Override protected Logger logger() {
    return LOG;
  }
}
