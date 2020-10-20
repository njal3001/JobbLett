package jobblett.json;

import com.fasterxml.jackson.databind.module.SimpleModule;
import jobblett.core.*;

public class CoreModule extends SimpleModule {
    public CoreModule() {
        super();

        super.addSerializer(Group.class, new GroupSerializer());
        super.addSerializer(GroupList.class, new GroupListSerializer());
        super.addSerializer(JobShift.class, new JobShiftSerializer());
        super.addSerializer(JobShiftList.class, new JobShiftListSerializer());
        super.addSerializer(UserList.class, new UserListSerializer());
        super.addSerializer(User.class, new UserSerializer());

        super.addDeserializer(Group.class, new GroupDeserializer());
        super.addDeserializer(GroupList.class, new GroupListDeserializer());
        super.addDeserializer(JobShift.class, new JobShiftDeserializer());
        super.addDeserializer(JobShiftList.class, new JobShiftListDeserializer());
        super.addDeserializer(User.class, new UserDeserializer());
        super.addDeserializer(UserList.class, new UserListDeserializer());

    }
}
