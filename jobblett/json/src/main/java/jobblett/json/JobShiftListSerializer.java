package jobblett.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.util.ArrayList;
import jobblett.core.JobShift;
import jobblett.core.JobShiftList;

class JobShiftListSerializer extends JsonSerializer<JobShiftList> {
  @Override public void serialize(JobShiftList o, JsonGenerator jsonGenerator,
      SerializerProvider serializerProvider) throws IOException {
    jsonGenerator.writeStartObject();

    ArrayList<JobShift> jobShifts = new ArrayList();
    o.forEach(jobShift -> jobShifts.add(jobShift));
    jsonGenerator.writeObjectField("jobShifts", jobShifts);

    jsonGenerator.writeEndObject();
  }
}
