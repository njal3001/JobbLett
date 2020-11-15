package jobblett.restapi;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import jobblett.core.User;
import jobblett.core.UserList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class UserListResource extends RestApiClass {
  public static final String USER_LIST_SERVICE_PATH = "userlist";
  protected static final Logger LOG = LoggerFactory.getLogger(UserListResource.class);

  private JobblettService jobblettService;

  private UserList tmpGetUserList() {
    return jobblettService.userList;
  }

  public UserListResource(JobblettService userList) {
    this.jobblettService = userList;
  }


  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public UserList getJobblettService() {
    return tmpGetUserList();
  }

  /**
   * Returns UserResource of a user with the same username (if one exist).
   *
   * @param userName user's username
   * @return UserResource
   */
  @Path("/get/{userName}")
  public UserResource getUser(@PathParam("userName") String userName) {
    User user = tmpGetUserList().get(userName);
    LOG.debug("Sub-resource for User " + userName + ": " + user);
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
  @Produces(MediaType.APPLICATION_JSON)
  public void add(User user) {
    try {
      tmpGetUserList().add(user);
      debug(user + " added into the UserList");
    } catch (Exception e) {
      debug(e.getMessage());
      debug("Something went wrong while adding the user: " + user);
    }
  }

  @Override protected Logger logger() {
    return LOG;
  }

  /**
   * Returns a User-object with the same username and password.
   *
   * @param userName the userName
   * @param hashedPassword the hashedPassword
   * @return logged in user
   */
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("/login/{userName}")
  public User login(@PathParam("userName") String userName, String hashedPassword) {
    User user = tmpGetUserList().checkUserNameAndPassword(userName, hashedPassword);
    debug("Logging in as " + user);
    return user;
  }
}
