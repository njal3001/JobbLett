package jobblett.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import jobblett.core.User;

public class UserSerializer extends StdSerializer<User> {

  protected UserSerializer() {
    super(User.class);
  }

  @Override
  public void serialize(User o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
      throws IOException {
    if (o == null) {
      jsonGenerator.writeNull();
      return;
    }
    jsonGenerator.writeStartObject();
    jsonGenerator.writeStringField("username", o.getUsername());
    jsonGenerator.writeStringField("password", o.getPassword().toString());
    jsonGenerator.writeStringField("givenName", o.getGivenName());
    jsonGenerator.writeStringField("familyName", o.getFamilyName());
    jsonGenerator.writeEndObject();
  }
}
