package jobblett.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import jobblett.core.Group;
import jobblett.core.GroupList;
import jobblett.core.Main;

import java.io.IOException;
import java.util.ArrayList;

public class MainSerializer extends StdSerializer<Main> {

    protected MainSerializer() {
        super(Main.class);
    }

    @Override
    public void serialize(Main o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();

        jsonGenerator.writeObjectField("userList", o.getUserList());
        jsonGenerator.writeObjectField("groupList", o.getGroupList());
        if (o.getLoggedIn() != null) jsonGenerator.writeStringField("loggedIn", o.getLoggedIn().getUserName());
        else jsonGenerator.writeStringField("loggedIn", "null");
        if (o.getActiveGroup() != null) jsonGenerator.writeNumberField("activeGroup", o.getActiveGroup().getGroupID());
        else jsonGenerator.writeStringField("activeGroup", "null");

        jsonGenerator.writeEndObject();
    }
}
