package jobblett.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import jobblett.core.Group;
import jobblett.core.JobShift;
import jobblett.core.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class JobShiftSerializer extends StdSerializer<JobShift> {

    protected JobShiftSerializer() {
        super(JobShift.class);
    }

    @Override
    public void serialize(JobShift o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();

        jsonGenerator.writeStringField("user",o.getUser().getUserName());
        jsonGenerator.writeStringField("startingTime",o.getStartingTime().toString());
        jsonGenerator.writeNumberField("duration",o.getDuration().getSeconds());
        jsonGenerator.writeStringField("info",o.getInfo());

        jsonGenerator.writeEndObject();
    }
}
