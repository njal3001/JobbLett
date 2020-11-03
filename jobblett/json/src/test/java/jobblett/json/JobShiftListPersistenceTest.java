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
public class JobShiftListPersistenceTest {

    JobShiftList jobShiftList = new JobShiftList();

    @BeforeAll
    public void setUp(){
        User olav = new User("olav", HashedPassword.hashPassword("bestePassord123"), "Olav", "Nordmann");
        User nora = new User("nora", HashedPassword.hashPassword("bestePassord123"), "Nora", "Bekkestad");

        JobShift jobShift1 = new JobShift(olav, LocalDateTime.parse("2021-10-10T17:10:53.798134"), Duration.ofSeconds(7200),"Cool info",true);
        JobShift jobShift2 = new JobShift(nora, LocalDateTime.now(), Duration.ofSeconds(7200),"Cool test info",true);
        jobShiftList.add(jobShift1);
        jobShiftList.add(jobShift2);
    }

    @Test
    public void persistenceTest() {

        // Serializing
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        mapper.registerModule(new JobblettCoreModule());
        String result = "";

        try {
            result = mapper.writeValueAsString(jobShiftList);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            fail(e);
        }

        // Deserializing
        mapper = new ObjectMapper();
        mapper.registerModule(new JobblettCoreModule());

        try {
            JobShiftList newJobShiftList = mapper.readValue(result,JobShiftList.class);
            assertTrue(newJobShiftList.equals(jobShiftList));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            fail(e);
        }

    }

    public static void main(String[] args) {
        JobShiftListPersistenceTest test = new JobShiftListPersistenceTest();
        test.setUp();
        test.persistenceTest();
    }
}
