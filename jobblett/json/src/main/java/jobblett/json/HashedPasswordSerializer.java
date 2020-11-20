package jobblett.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import jobblett.core.HashedPassword;

class HashedPasswordSerializer extends JsonSerializer<HashedPassword>  {

  @Override
  public void serialize(
      HashedPassword password,
      JsonGenerator jsonGenerator,
      SerializerProvider serializerProvider
  )
      throws IOException {
    jsonGenerator.writeStartObject();
    jsonGenerator.writeStringField("hashedPassword", password.toString());
    jsonGenerator.writeEndObject();
  }
}
