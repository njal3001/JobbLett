package jobblett.restapi;

import java.util.ArrayList;
import java.util.Collection;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import jobblett.core.Group;
import jobblett.core.User;

public class GroupResource {
  private Group group;

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

  /**
   * Returns all groupMembers as a list.
   * Will be serialized when using with rest server.
   *
   * @return list of users
   */
  @GET
  @Path("/members")
  @Produces(MediaType.APPLICATION_JSON)
  public Collection<User> getMembers() {
    Collection<User> users = new ArrayList<>();
    group.forEach(a -> users.add(a));
    return users;
  }

}
