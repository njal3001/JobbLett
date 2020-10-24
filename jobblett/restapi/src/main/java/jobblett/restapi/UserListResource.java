package jobblett.restapi;

import jobblett.core.User;
import jobblett.core.UserList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


public class UserListResource {
    public static final String USERLIST_SERVICE_PATH = "userlist";
    private static final Logger LOG = LoggerFactory.getLogger(UserListResource.class);

    private UserList userList;

    public UserListResource(UserList userList) {
        this.userList= userList;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public UserList getGroupList() {
        return userList;
    }

    @Path("/{userName}")
    public UserResource getUser(@PathParam("userName") String userName) {
        User user = userList.getUser(userName);
        LOG.debug("Sub-resource for User "+userName+": "+user);
        return new UserResource(user);
    }

}
