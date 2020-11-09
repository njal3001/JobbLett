package jobblett.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import jobblett.core.JobShift;

public class JobShiftSerializer extends JsonSerializer<JobShift> {

  @Override public void serialize(JobShift o, JsonGenerator jsonGenerator,
      SerializerProvider serializerProvider) throws IOException {
    jsonGenerator.writeStartObject();

    jsonGenerator.writeObjectField("user", o.getUser());
    jsonGenerator.writeStringField("startingTime", o.getStartingTime().toString());
    jsonGenerator.writeNumberField("duration", o.getDuration().getSeconds());
    jsonGenerator.writeStringField("info", o.getInfo());

    jsonGenerator.writeEndObject();
  }
}
