package jobblett.restserver;

import jobblett.core.GroupList;
import jobblett.core.UserList;
import org.glassfish.jersey.server.ResourceConfig;

public class JobblettConfig extends ResourceConfig{

    private UserList userList;
    private GroupList groupList;

    public JobblettConfig(UserList userList, GroupList groupList){

    }

}
