package jobblett.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import java.io.IOException;
import jobblett.core.User;
import jobblett.core.UserList;

public class UserListDeserializer extends StdDeserializer<UserList> {
  protected UserListDeserializer() {
    super(UserList.class);
  }


  @Override
  public UserList deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
      throws IOException, JsonProcessingException {
    JsonNode node = jsonParser.getCodec().readTree(jsonParser);

    ArrayNode arrayNode = (ArrayNode) node.get("users");
    UserList userList = new UserList();
    for (JsonNode userNode : arrayNode) {
      userList.add(JobblettDeserializer.deserialize(User.class, userNode));
    }
    return userList;
  }
}
