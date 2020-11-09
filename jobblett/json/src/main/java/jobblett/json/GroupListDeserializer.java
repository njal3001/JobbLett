package jobblett.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import java.io.IOException;
import jobblett.core.Group;
import jobblett.core.GroupList;

public class GroupListDeserializer extends JsonDeserializer<GroupList> {

  @Override
  public GroupList deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
      throws IOException {
    JsonNode node = jsonParser.getCodec().readTree(jsonParser);
    ArrayNode arrayNode = (ArrayNode) node.get("groups");
    GroupList groupList = new GroupList();
    for (JsonNode groupNode : arrayNode) {
      groupList.add(new JobblettPersistence().readValue(Group.class, groupNode));
    }
    return groupList;
  }
}
