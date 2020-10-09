package jobblett.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.IntNode;
import jobblett.core.User;

import java.io.IOException;

public class UserDeserializer extends JsonDeserializer<User> {

    public User deserialize(JsonNode node) throws IOException, JsonProcessingException {
        String username = node.get("username").asText();
        String password = node.get("password").asText();
        String givenName = node.get("givenName").asText();
        String familyName = node.get("familyName").asText();
        return new User(username,password,givenName,familyName,true);
    }

    @Override
    public User deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        TreeNode treeNode = jsonParser.getCodec().readTree(jsonParser);
        return deserialize((JsonNode) treeNode);
    }
}
