package jobblett.restapi;

import java.util.ArrayList;
import java.util.Collection;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import jobblett.core.Group;
import jobblett.core.JobShift;
import jobblett.core.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static jobblett.restapi.JobShiftListResource.JOB_SHIFT_LIST_RESOURCE_PATH;

public class GroupResource extends RestApiClass {
  private Group group;
  protected static final Logger LOG = LoggerFactory.getLogger(GroupResource.class);

  public GroupResource(Group group) {
    this.group = group;
  }

  /**
   * Returns the Group.
   *
   * @return Group-object
   */
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Group getGroup() {
    return group;
  }

  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("/add")
  public void addUser(User user) {
    group.addUser(user);
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/isAdmin/{userName}")
  public boolean isAdmin(@PathParam("userName") String userName) {
    User user = group.getUser(userName);
    if (user == null) {
      return false;
    }
    return group.isAdmin(user);
  }

  /**
   * Returns JobShiftResource.
   *
   * @return JobShiftResource
   */
  @GET
  @Path("/" + JOB_SHIFT_LIST_RESOURCE_PATH)
  @Produces(MediaType.APPLICATION_JSON)
  public JobShiftListResource getUser() {
    LOG.debug("Sub-resource for JobShiftList in Group: " + group);
    return new JobShiftListResource(group.getJobShiftList());
  }

  @Override protected Logger logger() {
    return LOG;
  }

}
