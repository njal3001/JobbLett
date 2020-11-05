package jobblett.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import java.util.ArrayList;
import jobblett.core.Group;
import jobblett.core.GroupList;

public class GroupListSerializer extends StdSerializer<GroupList> {

  protected GroupListSerializer() {
    super(GroupList.class);
  }

  @Override public void serialize(GroupList o, JsonGenerator jsonGenerator,
      SerializerProvider serializerProvider) throws IOException {
    jsonGenerator.writeStartObject();

    ArrayList<Group> groups = new ArrayList();
    o.forEach(group -> groups.add(group));
    jsonGenerator.writeObjectField("groups", groups);

    jsonGenerator.writeEndObject();
  }
}
