package jobblett.restapi;

import static jobblett.restapi.GroupListResource.GROUP_LIST_SERVICE_PATH;
import static jobblett.restapi.UserListResource.USER_LIST_SERVICE_PATH;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import jobblett.core.GroupList;
import jobblett.core.UserList;
import jobblett.core.Workspace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Path(JobblettService.JOBBLETT_SERVICE_PATH)
public class JobblettService extends RestApiClass {
  public static final String JOBBLETT_SERVICE_PATH = "jobblett";
  protected static final Logger LOG = LoggerFactory.getLogger(JobblettService.class);


  //TODO: should be private fields
  @Inject Workspace workspace;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public GroupList getGroupList() {
    return workspace.getGroupList();
  }

  @Path("/" + GROUP_LIST_SERVICE_PATH)
  public GroupListResource getGroupListResource() {
    debug("Sub-resource for GroupList");
    return new GroupListResource(workspace);
  }

  @Path("/" + USER_LIST_SERVICE_PATH)
  public UserListResource getUserListResource() {
    debug("Sub-resource for UserList");
    return new UserListResource(workspace);
  }

  /*
   * Replaces the existing userList with a new one.
   * Used by tests only.
   *
   * @param userList serialized UserList
   */
  /*@PUT
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("/setUserList")
  public void setUserList(UserList userList) {
    // TODO
    workspace = userList;
    debug(userList + " replaced existing userList");
  }*/

  /*
   * Replaces the existing groupList with a new one.
   * Used by tests only.
   *
   * @param groupList serialized GroupList string
   */
  /*@PUT
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("/setGroupList")
  public void setGroupList(GroupList groupList) {
    // TODO
    this.groupList = groupList;
    debug(groupList + " replaced existing groupList");
  }*/

  @Override protected Logger logger() {
    return LOG;
  }
}
