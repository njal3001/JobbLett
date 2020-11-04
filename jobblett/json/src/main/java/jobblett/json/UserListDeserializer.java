package jobblett.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import java.io.IOException;
import jobblett.core.UserList;

public class UserListDeserializer extends StdDeserializer<UserList> {
  protected UserListDeserializer() {
    super(UserList.class);
  }

  /**
   * TODO.
   *
   * @param node TODO
   * @return TODO
   * @throws IOException TODO
   * @throws JsonProcessingException TODO
   */
  public UserList deserialize(JsonNode node) throws IOException, JsonProcessingException {
    ArrayNode arrayNode = (ArrayNode) node.get("users");
    UserList userList = new UserList();
    for (JsonNode userNode : arrayNode) {
      userList.add(new UserDeserializer().deserialize(userNode));
    }
    return userList;
  }

  @Override
  public UserList deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
      throws IOException, JsonProcessingException {
    TreeNode treeNode = jsonParser.getCodec().readTree(jsonParser);
    return deserialize((JsonNode) treeNode);
  }
}
