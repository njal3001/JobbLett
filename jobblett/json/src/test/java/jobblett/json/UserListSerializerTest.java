package jobblett.json;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jobblett.core.User;
import jobblett.core.UserList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserListSerializerTest {

    private static ObjectMapper mapper;

    @BeforeEach
    public void setUp(){
        mapper = new ObjectMapper();
        mapper.registerModule(new CoreModule());
    }

    /*
    {"userList" : {
    "users" : [ {
      "username" : "Ola1424",
      "password" : "Godmorgen1234",
      "givenName" : "Ola",
      "familyName" : "Nordmann"
    }, {
      "username" : "Per2434",
      "password" : "Godkveld1234",
      "givenName" : "Per",
      "familyName" : "Gudmunsen"
    }, {
      "username" : "Herman3434",
      "password" : "Godettermiddag1234",
      "givenName" : "Herman",
      "familyName" : "Hermansen"
    },]
  }
    }

    */



    @Test
    public void testUserListSerializer(){
        UserList list = new UserList();
        User user1 = new User("Ola1424", "Godmorgen1234", "Ola","Nordmann");
        User user2 = new User("Per2434", "Godkveld1234", "Per","Gudmunsen");
        User user3 = new User("Herman3434", "Godettermiddag1234", "Herman","Hermansen");
        list.addUser(user1);
        list.addUser(user2);
        list.addUser(user3);

        try{

            assertEquals("{[{\"username\": \"Ola1424\"," +
                    "\"password\" : \"Godmorgen1234\"," +
                    "\"givenName\" : \"Ola\"," +
                    "\"familyName\" :\"Nordmann\"}," +
                    "{\\\"username\\\": \\\"Per2434\\\",\" +\n" +
                    "\"\\\"password\\\" : \\\"Godkveld1234\\\",\" +\n" +
                    "\"\\\"givenName\\\" : \\\"Per\\\",\" +\n" +
                    "\"\\\"familyName\\\" :\\\"Gudmunsen\\\"}," +
                    "{\\\"username\\\": \\\"Herman3434\\\",\" +\n" +
                    "\"\\\"password\\\" : \\\"Godettermiddag1234\\\",\" +\n" +
                    "\"\\\"givenName\\\" : \\\"Herman\\\",\" +\n" +
                    "\"\\\"familyName\\\" :\\\"Hermansen\\\"}]}", mapper.writeValueAsString(list));

        } catch (JsonProcessingException e){
            fail();
        }

    }

    public static void main(String[] args) {

    }

}
