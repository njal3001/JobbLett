package jobblett.restapi;

import java.util.Collection;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import jobblett.core.Group;
import jobblett.core.GroupList;
import jobblett.core.User;
import jobblett.json.JobblettDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class GroupListResource {
  public static final String GROUP_LIST_SERVICE_PATH = "grouplist";
  protected static final Logger LOG = LoggerFactory.getLogger(JobblettService.class);

  private GroupList groupList;

  public GroupListResource(GroupList groupList) {
    this.groupList = groupList;
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public GroupList getGroupList() {
    return groupList;
  }

  /**
   * Delegates to the right GroupResource by using the GroupID.
   *
   * @param groupIdString GroupID as a string
   * @return GroupResource instance
   */
  @Path("/get/{groupIdString}")
  public GroupResource getGroup(@PathParam("groupIdString") String groupIdString) {
    int groupId = Integer.parseInt(groupIdString);
    Group group = groupList.get(groupId);
    LOG.debug("Sub-resource for Group " + groupIdString + ": " + group);
    JobblettService.LOG.debug("Sub-resource for Group " + group.getGroupName() + ": " + group);
    return new GroupResource(group);
  }

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/new/{groupName}")
  public Group newGroup(@PathParam("groupName") String groupName) {
    return groupList.newGroup(groupName);
  }

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/getFromUsers/{userString}")
  public Collection<Group> getGroups(@PathParam("userString") String userString) {
    User user = JobblettDeserializer.deserialize(User.class, userString);
    return groupList.getGroups(user);
  }

}
