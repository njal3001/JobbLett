package jobblett.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import jobblett.core.HashedPassword;

class HashedPasswordDeserializer extends JsonDeserializer<HashedPassword> {

  @Override
  public HashedPassword deserialize(
      JsonParser jsonParser,
      DeserializationContext deserializationContext
  )
      throws IOException, JsonProcessingException {
    JsonNode node = jsonParser.getCodec().readTree(jsonParser);

    String hashedPassword = node.get("hashedPassword").asText();
    return HashedPassword.alreadyHashed(hashedPassword);
  }
}
