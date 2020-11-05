package jobblett.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import jobblett.core.HashedPassword;
import jobblett.core.User;

public class UserDeserializer extends JsonDeserializer<User> {

  /**
   * TODO.
   *
   * @param node TODO
   * @return TODO
   * @throws IOException TODO
   * @throws JsonProcessingException TODO
   */
  public User deserialize(JsonNode node) throws IOException, JsonProcessingException {
    if (node.isNull()) {
      return null;
    }
    String username = node.get("username").asText();
    String password = node.get("password").asText();
    String givenName = node.get("givenName").asText();
    String familyName = node.get("familyName").asText();
    return new User(username, HashedPassword.alreadyHashed(password), givenName, familyName);
  }

  @Override
  public User deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
      throws IOException, JsonProcessingException {
    TreeNode treeNode = jsonParser.getCodec().readTree(jsonParser);
    return deserialize((JsonNode) treeNode);
  }
}
