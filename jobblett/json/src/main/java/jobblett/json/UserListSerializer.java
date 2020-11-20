package jobblett.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.util.ArrayList;
import jobblett.core.User;
import jobblett.core.UserList;

class UserListSerializer extends JsonSerializer<UserList> {

  @Override public void serialize(UserList o, JsonGenerator jsonGenerator,
      SerializerProvider serializerProvider) throws IOException {
    jsonGenerator.writeStartObject();

    ArrayList<User> users = new ArrayList<>();
    o.forEach(users::add);
    jsonGenerator.writeObjectField("users", users);

    jsonGenerator.writeEndObject();
  }
}
