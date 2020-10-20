package jobblett.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jobblett.core.Group;
import jobblett.core.GroupList;
import jobblett.core.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GroupListPersistenceTest {

    GroupList groupList = new GroupList();

    @BeforeAll
    public void setUp(){
        User olav = new User("olav", "bestePassord123", "Olav", "Nordmann");
        User nora = new User("nora", "bestePassord123", "Nora", "Bekkestad");
        User petter = new User("petter", "bestePassord123", "Petter", "Petterson");
        User david = new User("david", "bestePassord123", "David", "Berg");

        Group group1 = groupList.newGroup("TestGroup1");
        group1.addUser(olav);
        group1.addUser(nora);

        Group group2 = groupList.newGroup("TestGroup2");
        group2.addUser(petter);
        group2.addUser(david);
    }

    @Test
    public void persistenceTest() {

        // Serializing
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        mapper.registerModule(new CoreModule());
        String result = "";

        try {
            result = mapper.writeValueAsString(groupList);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            fail(e);
        }

        // Deserializing
        mapper = new ObjectMapper();
        mapper.registerModule(new CoreModule());

        try {
            GroupList newGroupList = mapper.readValue(result,GroupList.class);
            assertTrue(newGroupList.equals(groupList));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            fail(e);
        }

    }

    public static void main(String[] args) {
        GroupListPersistenceTest test = new GroupListPersistenceTest();
        test.setUp();
        test.persistenceTest();
    }
}
