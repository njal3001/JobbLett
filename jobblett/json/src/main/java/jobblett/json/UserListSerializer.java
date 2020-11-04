package jobblett.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import java.util.ArrayList;
import jobblett.core.User;
import jobblett.core.UserList;

public class UserListSerializer extends StdSerializer<UserList> {

  protected UserListSerializer() {
    super(UserList.class);
  }

  @Override public void serialize(UserList o, JsonGenerator jsonGenerator,
      SerializerProvider serializerProvider) throws IOException {
    jsonGenerator.writeStartObject();

    ArrayList<User> users = new ArrayList();
    o.forEach(user -> users.add(user));
    jsonGenerator.writeObjectField("users", users);

    jsonGenerator.writeEndObject();
  }
}
