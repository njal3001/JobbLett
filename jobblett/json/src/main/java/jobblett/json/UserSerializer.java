package jobblett.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import jobblett.core.User;

class UserSerializer extends JsonSerializer<User> {

  @Override
  public void serialize(User o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
      throws IOException {
    if (o == null) {
      jsonGenerator.writeNull();
      return;
    }
    jsonGenerator.writeStartObject();
    jsonGenerator.writeStringField("username", o.getUsername());
    jsonGenerator.writeObjectField("password", o.getPassword());
    jsonGenerator.writeStringField("givenName", o.getGivenName());
    jsonGenerator.writeStringField("familyName", o.getFamilyName());
    jsonGenerator.writeEndObject();
  }
}
