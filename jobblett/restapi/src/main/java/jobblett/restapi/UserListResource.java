package jobblett.restapi;

import jobblett.core.User;
import jobblett.core.UserList;
import jobblett.json.JobblettDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;


public class UserListResource {
    public static final String USER_LIST_SERVICE_PATH = "userlist";
    protected static final Logger LOG = LoggerFactory.getLogger(JobblettService.class);

    private UserList userList;

    public UserListResource(UserList userList) {
        this.userList= userList;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public UserList getUserList() {
        return userList;
    }

    @Path("get/{userName}")
    public UserResource getUser(@PathParam("userName") String userName) {
        User user = userList.get(userName);
        LOG.debug("Sub-resource for User "+userName+": "+user);
        return new UserResource(user);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/add/{userString}")
    public boolean add(@PathParam("userString") String userString){
        User user = new JobblettDeserializer<User>(User.class).deserializeString(userString);
        return userList.add(user);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/login/{userNameAndPassword}")
    public User login(@PathParam("userNameAndPassword") Collection<String> userNameAndPassword){
        Iterator<String> iterator = userNameAndPassword.iterator();
        String userName = iterator.next();
        String password = iterator.next();
        return userList.checkUserNameAndPassword(userName,password);
    }
}
