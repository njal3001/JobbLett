package jobblett.restapi;

import jobblett.core.GroupList;
import jobblett.core.UserList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path(JobblettService.GROUPLIST_SERVICE_PATH)
public class JobblettService {
    public static final String GROUPLIST_SERVICE_PATH = "grouplist";
    protected static final Logger LOG = LoggerFactory.getLogger(JobblettService.class);

    @Inject
    private GroupList groupList;

    @Inject
    private UserList userList;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public GroupList getGroupList() {
        return groupList;
    }

    @Path("/grouplist")
    public GroupListResource getGroupListResource() {
        return new GroupListResource(groupList);
    }

    @Path("/userlist")
    public UserListResource getUserListResource() {
        return new UserListResource(userList);
    }

}
