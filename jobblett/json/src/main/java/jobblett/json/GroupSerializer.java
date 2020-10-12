package jobblett.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import jobblett.core.Group;
import jobblett.core.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class GroupSerializer extends StdSerializer<Group> {

    protected GroupSerializer() {
        super(Group.class);
    }

    @Override
    public void serialize(Group o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();

        jsonGenerator.writeStringField("groupName",o.getGroupName());

        ArrayList<User> groupMembers = new ArrayList();
        o.iterator().forEachRemaining(groupMembers::add);
        //Collection<String> usernames = groupMembers.stream().map(u -> u.getUserName()).collect(Collectors.toList());
        jsonGenerator.writeObjectField("groupMembers",groupMembers);

        jsonGenerator.writeNumberField("groupID",o.getGroupID());
        jsonGenerator.writeObjectField("jobShifts",o.getJobShifts());
        jsonGenerator.writeStringField("admin",o.getAdmin().getUserName());

        jsonGenerator.writeEndObject();
    }
}
