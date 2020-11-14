package jobblett.restapi;

import java.util.Collection;
import java.util.Iterator;
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
import jobblett.json.JobblettPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class UserListResource {
  public static final String USER_LIST_SERVICE_PATH = "userlist";
  protected static final Logger LOG = LoggerFactory.getLogger(JobblettService.class);

  private UserList userList;

  public UserListResource(UserList userList) {
    this.userList = userList;
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public UserList getUserList() {
    return userList;
  }

  /**
   * Returns UserResource of a user with the same username (if one exist).
   *
   * @param userName user's username
   * @return UserResource
   */
  @Path("get/{userName}")
  public UserResource getUser(@PathParam("userName") String userName) {
    User user = userList.get(userName);
    LOG.debug("Sub-resource for User " + userName + ": " + user);
    return new UserResource(user);
  }

  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public boolean add(User user) {
    return userList.add(user);
  }

  /*
   * Returns a User-object with the same username and password.
   *
   * @param userNameAndPassword username and password as a collection of strings
   * @return logged in user
   */
  /*@GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/login/{userNameAndPassword}")
  public User login(@PathParam("userNameAndPassword") Collection<String> userNameAndPassword) {
    Iterator<String> iterator = userNameAndPassword.iterator();
    String userName = iterator.next();
    String password = iterator.next();
    return userList.checkUserNameAndPassword(userName, new HashedPassword(password));
  }*/
}
