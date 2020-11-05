package jobblett.restapi;

import static jobblett.restapi.GroupListResource.GROUP_LIST_SERVICE_PATH;
import static jobblett.restapi.UserListResource.USER_LIST_SERVICE_PATH;

import java.util.Collection;
import java.util.Iterator;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import jobblett.core.GroupList;
import jobblett.core.JobblettList;
import jobblett.core.UserList;
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
   * TODO.
   *
   * @param userListAndGroupListStrings TODO
   * @return TODO
   */
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/setlists/{userListAndGroupListStrings}")
  public boolean login(@PathParam("userListAndGroupListStrings")
      Collection<JobblettList> userListAndGroupListStrings) {
    Iterator<JobblettList> iterator = userListAndGroupListStrings.iterator();
    UserList userList = (UserList) iterator.next();
    GroupList groupList = (GroupList) iterator.next();
    this.userList = userList;
    this.groupList = groupList;
    return true;
  }

}
