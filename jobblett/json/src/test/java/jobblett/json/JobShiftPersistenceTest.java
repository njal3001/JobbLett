package jobblett.json;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.time.Duration;
import java.time.LocalDateTime;
import jobblett.core.HashedPassword;
import jobblett.core.JobShift;
import jobblett.core.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS) public class JobShiftPersistenceTest extends AbstractPersistenceTest {

  JobShift jobShift;

  public JobShiftPersistenceTest() {
    super(JobShift.class);
  }


  @BeforeAll public void setUp() {
    User olav =
        new User("olav", HashedPassword.hashPassword("bestePassord123"), "Olav", "Nordmann");
    jobShift = new JobShift(olav, LocalDateTime.parse("2021-10-10T17:10:53.798134"),
        Duration.ofSeconds(7200), "Cool info");
  }

  @Override public Object getObject() {
    return jobShift;
  }
}
