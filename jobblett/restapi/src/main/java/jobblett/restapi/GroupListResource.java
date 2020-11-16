package jobblett.restapi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import jobblett.core.Group;
import jobblett.core.GroupList;
import jobblett.core.JobShift;
import jobblett.core.User;
import jobblett.core.UserList;
import jobblett.core.Workspace;
import jobblett.json.JobblettPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class GroupListResource extends RestApiClass {
  public static final String GROUP_LIST_SERVICE_PATH = "grouplist";
  protected static final Logger LOG = LoggerFactory.getLogger(GroupListResource.class);

  private Workspace workspace;


  public GroupListResource(Workspace workspace) {
    this.workspace = workspace;
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public GroupList getGroupList() {
    return workspace.getGroupList();
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
    Group group = workspace.getGroupList().get(groupId);
    debug("Sub-resource for Group " + group.getGroupName() + ": " + group);
    return new GroupResource(group);
  }

  /**
   * Creates a new Group with the specified groupName.
   *
   * @param groupName the specified groupName
   * @return the created group
   */
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/new/{groupName}")
  public Group newGroup(@PathParam("groupName") String groupName) {
    Group group = workspace.getGroupList().newGroup(groupName);
    debug("New group: " + group);
    return group;
  }

  /**
   * Returns a list of groups which has the specified User as a group member.
   *
   * @param user the specified user
   * @return a list of groups
   */
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("/getFromUsers/")
  public GroupList getGroups(User user) {
    // TODO: find a equal object instead of only using the username
    //correctGroupList(workspace.getGroupList(), workspace.getUserList());

    debug("Returns every group " + user + "is a member of.");
    user = workspace.getUserList().get(user.getUsername());
    GroupList groupList = new GroupList();
    groupList.addAll(workspace.getGroupList().getGroups(user));
    return groupList;
  }

  /**
   * Replaces a group with the same groupId with the specified group.
   *
   * @param group specified group
   * @return if contained
   */
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("/replaceGroup/")
  public boolean replaceGroup(Group group) {
    Collection<Integer> groupIds = workspace.getGroupList()
        .stream()
        .map(Group::getGroupId)
        .collect(Collectors.toList());
    if (!groupIds.contains(group.getGroupId())) {
      debug("Could not replace with group: " + group);
      return false;
    }
    Group toBeRemoved = workspace.getGroupList().get(group.getGroupId());
    workspace.getGroupList().remove(toBeRemoved);
    workspace.getGroupList().add(group);
    debug("Replaced to: " + group);
    return true;
  }

  @Override protected Logger logger() {
    return LOG;
  }
}
