package jobblett.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import jobblett.core.Group;
import jobblett.core.User;

import java.io.IOException;

public class UserSerializer extends StdSerializer<User> {

    protected UserSerializer() {
        super(User.class);
    }

    @Override
    public void serialize(User o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (o == null) {
            jsonGenerator.writeNull();
            return;
        }
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("username",o.getUserName());
        jsonGenerator.writeStringField("password",o.getHashedPassword());
        jsonGenerator.writeStringField("givenName",o.getGivenName());
        jsonGenerator.writeStringField("familyName",o.getFamilyName());
        jsonGenerator.writeEndObject();
    }
}