package jobblett.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import java.io.IOException;
import jobblett.core.GroupList;

public class GroupListDeserializer extends StdDeserializer<GroupList> {
  protected GroupListDeserializer() {
    super(GroupList.class);
  }

  /**
   * TODO.
   *
   * @param node TODO
   * @return TODO
   * @throws IOException TODO
   */
  public GroupList deserialize(JsonNode node) throws IOException {
    ArrayNode arrayNode = (ArrayNode) node.get("groups");
    GroupList groupList = new GroupList();
    for (JsonNode groupNode : arrayNode) {
      groupList.add(new GroupDeserializer().deserialize(groupNode));
    }
    return groupList;
  }

  @Override
  public GroupList deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
      throws IOException {
    TreeNode treeNode = jsonParser.getCodec().readTree(jsonParser);
    return deserialize((JsonNode) treeNode);
  }
}
