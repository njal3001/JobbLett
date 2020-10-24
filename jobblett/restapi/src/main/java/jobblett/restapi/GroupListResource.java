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


public class GroupListResource {
    public static final String GROUPLIST_SERVICE_PATH = "grouplist";
    private static final Logger LOG = LoggerFactory.getLogger(GroupListResource.class);

    private GroupList groupList;

    public GroupListResource(GroupList groupList) {
        this.groupList = groupList;
    }

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
        JobblettService.LOG.debug("Sub-resource for Group "+group.getGroupName()+": "+group);
        return new GroupResource(group);
    }
}
