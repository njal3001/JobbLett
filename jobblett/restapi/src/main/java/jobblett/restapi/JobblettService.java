package jobblett.restapi;

import static jobblett.restapi.GroupListResource.GROUP_LIST_SERVICE_PATH;
import static jobblett.restapi.UserListResource.USER_LIST_SERVICE_PATH;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import jobblett.core.GroupList;
import jobblett.core.UserList;
import jobblett.json.JobblettPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Path(JobblettService.JOBBLETT_SERVICE_PATH)
public class JobblettService {
  public static final String JOBBLETT_SERVICE_PATH = "jobblett";
  protected static final Logger LOG = LoggerFactory.getLogger(JobblettService.class);

  @Inject private GroupList groupList;

  @Inject private UserList userList;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public GroupList getGroupList() {
    return groupList;
  }

  @Path("/" + GROUP_LIST_SERVICE_PATH)
  public GroupListResource getGroupListResource() {
    return new GroupListResource(groupList);
  }

  @Path("/" + USER_LIST_SERVICE_PATH)
  public UserListResource getUserListResource() {
    return new UserListResource(userList);
  }

  /**
   * Replaces the existing userList with a new one.
   * Used by tests only.
   *
   * @param userListString serialized UserList string
   * @return true if the userList was replaced
   */
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/setUserList/{userListString}")
  public boolean setUserList(@PathParam("userListString") String userListString) {
    try {
      UserList userList = new JobblettPersistence().readValue(UserList.class, userListString);
      this.userList = userList;
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }

  }

  /**
   * Replaces the existing groupList with a new one.
   * Used by tests only.
   *
   * @param groupListString serialized GroupList string
   * @return true if the groupList was replaced
   */
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/setGroupList/{groupListString}")
  public boolean setGroupList(@PathParam("groupListString") String groupListString) {
    try {
      GroupList groupList = new JobblettPersistence().readValue(GroupList.class, groupListString);
      this.groupList = groupList;
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

}
