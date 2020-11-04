package jobblett.restapi;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import jobblett.core.User;

public class UserResource {
  private User user;

  public UserResource(User user) {
    this.user = user;
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public User getUser() {
    return user;
  }
}
