package jobblett.restserver;

import jobblett.core.GroupList;
import jobblett.core.UserList;
import jobblett.json.JobblettDeserializer;
import jobblett.json.UserListDeserializer;
import jobblett.restapi.JobblettService;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import java.net.URL;

public class JobblettConfig extends ResourceConfig{

    private UserList userList;
    private GroupList groupList;

    public JobblettConfig(UserList userList, GroupList groupList){
        setUserList(userList);
        setGroupList(groupList);
        register(JobblettService.class);
        register(JobblettModuleObjectMapperProvider.class);
        register(JacksonFeature.class);
        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(JobblettConfig.this.userList);
                bind(JobblettConfig.this.groupList);
            }
        });

    }

    public JobblettConfig(){
        this(createDefaultUserList(),createDefaultGroupList());
    }

    public void setUserList(UserList userList) {
        this.userList = userList;
    }

    public void setGroupList(GroupList groupList) {
        this.groupList = groupList;
    }

    private static UserList createDefaultUserList(){
       return JobblettDeserializer.useDefaultValues(UserList.class);

    }

    private static GroupList createDefaultGroupList(){
        return JobblettDeserializer.useDefaultValues(GroupList.class);
    }
}
