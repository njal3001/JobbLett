package jobblett.restapi;

import jobblett.core.Group;
import jobblett.core.GroupList;
import jobblett.core.User;
import jobblett.json.JobblettDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;


public class GroupListResource {
    public static final String GROUP_LIST_SERVICE_PATH = "grouplist";
    protected static final Logger LOG = LoggerFactory.getLogger(JobblettService.class);

    private GroupList groupList;

    public GroupListResource(GroupList groupList) {
        this.groupList = groupList;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public GroupList getGroupList() {
        return groupList;
    }

    @Path("/get/{groupIDString}")
    public GroupResource getGroup(@PathParam("groupIDString") String groupIDString) {
        int groupID = Integer.parseInt(groupIDString);
        Group group = groupList.get(groupID);
        LOG.debug("Sub-resource for Group "+groupIDString+": "+group);
        JobblettService.LOG.debug("Sub-resource for Group "+group.getGroupName()+": "+group);
        return new GroupResource(group);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/new/{groupName}")
    public Group newGroup(@PathParam("groupName") String groupName){
        return groupList.newGroup(groupName);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getFromUsers/{userString}")
    public Collection<Group> getGroups(@PathParam("userString") String userString){
        User user = new JobblettDeserializer<User>(User.class).deserializeString(userString);
        return groupList.getGroups(user);
    }

}
