package jobblett.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import jobblett.core.JobShift;
import jobblett.core.JobShiftList;
import jobblett.core.User;
import jobblett.core.UserList;

import java.io.IOException;
import java.util.ArrayList;

public class UserListSerializer extends StdSerializer<UserList> {

    protected UserListSerializer() {
        super(UserList.class);
    }

    @Override
    public void serialize(UserList o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();

        ArrayList<User> users = new ArrayList();
        o.forEach(user -> users.add(user));
        jsonGenerator.writeObjectField("users",users);

        jsonGenerator.writeEndObject();
    }
}
