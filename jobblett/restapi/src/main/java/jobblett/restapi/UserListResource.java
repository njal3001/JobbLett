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
  public static final String USER_LIST_RESCORCE_PATH = "userlist";
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
    debug("Returns UserList.");
    return userList;
  }

  /**
   * Returns UserResource of a user with the same username (if one exist).
   *
   * @param username user's username
   * @return UserResource
   */
  @GET
  @Path("/get/{userName}")
  @Produces(MediaType.APPLICATION_JSON) // TODO trengs denne? sjekk dette på alle
  public UserResource getUser(@PathParam("userName") String userName) {
    checkUsername(userName);
    User user = userList.get(userName);
    LOG.debug("Sub-resource for User " + userName + ": " + user);
    return new UserResource(user);
  }

  /**
   * Returns if a user with the same username exist.
   *
   * @param userName user's username
   * @return boolean
   */
  @GET
  @Path("/exist/{userName}")
  @Produces(MediaType.APPLICATION_JSON)
  public boolean exist(@PathParam("userName") String userName) {
    boolean exist = (userList.get(userName) != null);
    LOG.debug("Returns if the user " + userName + "exist: " + exist);
    return exist;
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
