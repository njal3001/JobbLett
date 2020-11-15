package jobblett.restapi;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import jobblett.core.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserResource extends RestApiClass {
  protected static final Logger LOG = LoggerFactory.getLogger(User.class);
  private User user;

  public UserResource(User user) {
    this.user = user;
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public User getUser() {
    debug("Returns the user: " + user);
    return user;
  }

  @Override protected Logger logger() {
    return LOG;
  }
}
