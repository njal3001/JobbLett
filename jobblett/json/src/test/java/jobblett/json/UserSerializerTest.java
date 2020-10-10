package jobblett.json;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jobblett.core.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserSerializerTest {

    private static ObjectMapper mapper;

    @BeforeEach
    public void setUp(){
        mapper = new ObjectMapper();
        mapper.registerModule(new CoreModule());
    }

    /*
    {
    User:
      "username" : "olav",
      "password" : "4eedeb97e95c27b0479f786733b3a71bc47a2f61f795cac1b29bc77b58b4df71",
      "givenName" : "Olav",
      "familyName" : "Nordmann"
    }

    */

    @Test
    public void testUserSerializer() throws JsonProcessingException {
        User user = new User("Olavh123","Heisann123456", "Olav", "Hermansen");
        try{
            assertEquals( "{\"username\": \"Olavh123\", \"password\" : \"Heisann123456\", \"givenName\" : \"Olav\", \"familyName\" :\"Hermansen\"}",mapper.writeValueAsString(user));
        } catch(JsonProcessingException e){
            fail();
        }
    }


    public static void main(String[] args) {

    }

}
