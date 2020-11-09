package jobblett.restserver;

import jobblett.core.GroupList;
import jobblett.core.UserList;
import jobblett.json.JobblettPersistence;
import jobblett.restapi.JobblettService;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

public class JobblettConfig extends ResourceConfig {
  private UserList userList;
  private GroupList groupList;

  private JobblettConfig(UserList userList, GroupList groupList) {
    setUserList(userList);
    setGroupList(groupList);
    register(JobblettService.class);
    register(JobblettModuleObjectMapperProvider.class);
    register(JacksonFeature.class);
    register(new AbstractBinder() {
      @Override protected void configure() {
        bind(JobblettConfig.this.userList);
        bind(JobblettConfig.this.groupList);
      }
    });

  }

  public JobblettConfig() {
    this(createDefaultUserList(), createDefaultGroupList());
  }

  public void setUserList(UserList userList) {
    this.userList = userList;
  }

  public void setGroupList(GroupList groupList) {
    this.groupList = groupList;
  }

  private static UserList createDefaultUserList() {
    return new JobblettPersistence().readDefault(UserList.class);

  }

  private static GroupList createDefaultGroupList() {
    return new JobblettPersistence().readDefault(GroupList.class);
  }
}
