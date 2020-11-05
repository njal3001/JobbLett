package jobblett.restapi;

import java.util.ArrayList;
import java.util.Collection;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
   * TODO.
   *
   * @return TODO
   */
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Collection<User> getUsers() {
    Collection<User> users = new ArrayList<>();
    group.forEach(a -> users.add(a));
    return users;
  }

  /**
   * TODO.
   *
   * @param userName TODO
   * @return TODO
   */
  // Maybe unnecessary
  @Path("/{userName}")
  public UserResource getUser(@PathParam("userName") String userName) {
    User user = group.getUser(userName);
    JobblettService.LOG.debug("Sub-resource for Group " + group.getGroupName() + ": " + group);
    return new UserResource(user);
  }

}
