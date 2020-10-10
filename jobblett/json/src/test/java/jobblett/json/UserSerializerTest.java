package jobblett.json;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Iterator;

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
