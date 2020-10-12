package jobblett.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jobblett.core.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MainPersistenceTest {

    Main main = new Main();

    @BeforeAll
    public void setUp(){
        Group gruppe7 = main.getGroupList().newGroup("Gruppe7");

        main.getUserList().addUser(new User("olav", "bestePassord123", "Olav", "Nordmann"));
        main.getUserList().addUser(new User("nora", "bestePassord123", "Nora", "Bekkestad"));
        main.getUserList().addUser(new User("petter", "bestePassord123", "Petter", "Petterson"));
        main.getUserList().addUser(new User("david", "bestePassord123", "David", "Berg"));

        gruppe7.addUser(main.getUserList().getUser("olav"));
        gruppe7.addUser(main.getUserList().getUser("nora"));
        gruppe7.addUser(main.getUserList().getUser("petter"));
        gruppe7.addUser(main.getUserList().getUser("david"));

        gruppe7.getJobShifts().addJobShift(new JobShift(main.getUserList().getUser("nora"), LocalDateTime.now().plusYears(1), Duration.ofHours(2),"Dette er Olav sin skift."));
    }

    @Test
    public void persistenceTest() {

        // Serializing
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        mapper.registerModule(new CoreModule());
        String result = "";

        try {
            result = mapper.writeValueAsString(main);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            fail(e);
        }

        // Deserializing
        mapper = new ObjectMapper();
        mapper.registerModule(new CoreModule());

        try {
            Main newMain = mapper.readValue(result,Main.class);
            assertTrue(newMain.equals(main));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            fail(e);
        }

    }

    public static void main(String[] args) {
        MainPersistenceTest test = new MainPersistenceTest();
        test.setUp();
        test.persistenceTest();
    }
}
