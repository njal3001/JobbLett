package jobblett.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jobblett.core.Group;
import jobblett.core.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GroupPersistenceTest {

    Group group = new Group("TestGroup", ThreadLocalRandom.current().nextInt(1000,10000));

    @BeforeAll
    public void setUp(){
        User olav = new User("olav", "bestePassord123", "Olav", "Nordmann");
        User nora = new User("nora", "bestePassord123", "Nora", "Bekkestad");
        User petter = new User("petter", "bestePassord123", "Petter", "Petterson");
        User david = new User("david", "bestePassord123", "David", "Berg");

        group.addUser(olav);
        group.addUser(nora);
    }

    @Test
    public void persistenceTest() {

        // Serializing
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        mapper.registerModule(new JobblettCoreModule());
        String result = "";

        try {
            result = mapper.writeValueAsString(group);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            fail(e);
        }

        // Deserializing
        mapper = new ObjectMapper();
        mapper.registerModule(new JobblettCoreModule());

        try {
            Group newGroup = mapper.readValue(result,Group.class);
            assertTrue(newGroup.equals(group));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            fail(e);
        }

    }

    public static void main(String[] args) {
        GroupPersistenceTest test = new GroupPersistenceTest();
        test.setUp();
        test.persistenceTest();
    }
}
