package jobblett.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jobblett.core.Group;
import jobblett.core.JobShift;
import jobblett.core.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class JobShiftPersistenceTest {

    JobShift jobShift;

    @BeforeAll
    public void setUp(){
        User olav = new User("olav", "bestePassord123", "Olav", "Nordmann");
        jobShift = new JobShift(olav, LocalDateTime.parse("2021-10-10T17:10:53.798134"), Duration.ofSeconds(7200),"Cool info",true);
    }

    @Test
    public void persistenceTest() {

        // Serializing
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        mapper.registerModule(new CoreModule());
        String result = "";

        try {
            result = mapper.writeValueAsString(jobShift);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            fail(e);
        }

        // Deserializing
        mapper = new ObjectMapper();
        mapper.registerModule(new CoreModule());

        try {
            JobShift newGroup = mapper.readValue(result,JobShift.class);
            assertTrue(newGroup.equals(jobShift));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            fail(e);
        }

    }

    public static void main(String[] args) {
        JobShiftPersistenceTest test = new JobShiftPersistenceTest();
        test.setUp();
        test.persistenceTest();
    }
}
