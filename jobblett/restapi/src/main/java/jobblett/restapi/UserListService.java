package jobblett.restapi;

import jobblett.core.Group;
import jobblett.core.GroupList;
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


@Path(UserListService.USERLIST_SERVICE_PATH)
public class UserListService {
    public static final String USERLIST_SERVICE_PATH = "userlist";
    private static final Logger LOG = LoggerFactory.getLogger(UserListService.class);

    @Inject
    private UserList userList;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public UserList getGroupList() {
        return userList;
    }

    @Path("/{userName}")
    public UserResource getGroup(@PathParam("userName") String userName) {
        User user = userList.getUser(userName);
        LOG.debug("Sub-resource for User "+userName+": "+user);
        return new UserResource(user);
    }

}
