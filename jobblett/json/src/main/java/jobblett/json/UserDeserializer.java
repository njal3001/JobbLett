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

  @Override
  public User deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
      throws IOException, JsonProcessingException {
    JsonNode node = jsonParser.getCodec().readTree(jsonParser);

    if (node.isNull()) {
      return null;
    }
    String username = node.get("username").asText();
    HashedPassword password = new JobblettPersistence()
        .readValue(HashedPassword.class, node.get("password"));
    String givenName = node.get("givenName").asText();
    String familyName = node.get("familyName").asText();
    return new User(username, password, givenName, familyName);
  }
}
