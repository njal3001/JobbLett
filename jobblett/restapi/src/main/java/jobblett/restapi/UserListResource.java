package jobblett.restapi;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import jobblett.core.HashedPassword;
import jobblett.core.User;
import jobblett.core.UserList;
import jobblett.core.Workspace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class UserListResource extends RestApiClass {
  public static final String USER_LIST_SERVICE_PATH = "userlist";
  protected static final Logger LOG = LoggerFactory.getLogger(UserListResource.class);

  private UserList userList;

  public UserListResource(UserList userList) {
    this.userList = userList;
  }

  private void checkUsername(String username) {
    if (userList.get(username) == null) {
      throw new IllegalArgumentException("No user with username: " + username);
    }
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public UserList getJobblettService() {
    return userList;
  }

  /**
   * Returns UserResource of a user with the same username (if one exist).
   *
   * @param userName user's username
   * @return UserResource
   */
  @Path("/get/{username}")
  public UserResource getUser(@PathParam("username") String username) {
    checkUsername(username);
    User user = userList.get(username);
    LOG.debug("Sub-resource for User " + username + ": " + user);
    return new UserResource(user);
  }

  /**
   * Adds the specified user into the userList.
   *
   * @param user the specified user
   */
  @PUT
  @Path("/add")
  @Consumes(MediaType.APPLICATION_JSON)
  public void add(User user) {
    userList.add(user);
  }

  @Override protected Logger logger() {
    return LOG;
  }

}
