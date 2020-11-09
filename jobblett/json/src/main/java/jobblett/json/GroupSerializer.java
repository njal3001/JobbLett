package jobblett.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import java.util.ArrayList;
import jobblett.core.Group;
import jobblett.core.User;

public class GroupSerializer extends JsonSerializer<Group> {
  @Override
  public void serialize(Group o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
      throws IOException {
    jsonGenerator.writeStartObject();

    jsonGenerator.writeStringField("groupName", o.getGroupName());

    ArrayList<User> groupMembers = new ArrayList();
    o.iterator().forEachRemaining(groupMembers::add);
    jsonGenerator.writeObjectField("groupMembers", groupMembers);

    jsonGenerator.writeNumberField("groupId", o.getGroupId());
    jsonGenerator.writeObjectField("jobShifts", o.getJobShifts());
    jsonGenerator.writeObjectField("admins", o.getAdmins());

    jsonGenerator.writeEndObject();
  }
}
