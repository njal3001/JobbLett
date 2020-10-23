package jobblett.restapi;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import jobblett.core.Group;
import jobblett.core.GroupList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Path(GroupListService.GROUPLIST_SERVICE_PATH)
public class GroupListService {
    public static final String GROUPLIST_SERVICE_PATH = "grouplist";
    private static final Logger LOG = LoggerFactory.getLogger(GroupListService.class);

    @Inject
    private GroupList groupList;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public GroupList getGroupList() {
        return groupList;
    }

    @Path("/{groupIDString}")
    public GroupResource getGroup(@PathParam("groupIDString") String groupIDString) {
        int groupID = Integer.parseInt(groupIDString);
        Group group = groupList.getGroup(groupID);
        LOG.debug("Sub-resource for Group "+groupIDString+": "+group);
        return new GroupResource(group);
    }

}
