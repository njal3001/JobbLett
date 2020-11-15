package jobblett.json;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import jobblett.core.HashedPassword;

public class HashedPasswordSerializer extends JsonSerializer<HashedPassword> {

  @Override
  public void serialize(HashedPassword password, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) 
    throws IOException {
    jsonGenerator.writeStartObject();
    jsonGenerator.writeStringField("password", password.toString());
    jsonGenerator.writeEndObject();
  }
}