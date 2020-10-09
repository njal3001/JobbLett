package jobblett.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import jobblett.core.Group;
import jobblett.core.GroupList;
import jobblett.core.JobShift;
import jobblett.core.JobShiftList;

import java.io.IOException;
import java.util.ArrayList;

public class JobShiftListSerializer extends StdSerializer<JobShiftList> {

    protected JobShiftListSerializer() {
        super(JobShiftList.class);
    }

    @Override
    public void serialize(JobShiftList o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();

        ArrayList<JobShift> jobShifts = new ArrayList();
        o.forEach(jobShift -> jobShifts.add(jobShift));
        jsonGenerator.writeObjectField("jobShifts",jobShifts);

        jsonGenerator.writeEndObject();
    }
}
