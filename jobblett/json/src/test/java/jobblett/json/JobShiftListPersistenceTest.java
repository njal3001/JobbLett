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
import jobblett.core.JobShiftList;
import jobblett.core.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS) public class JobShiftListPersistenceTest extends AbstractPersistenceTest{

  JobShiftList jobShiftList = new JobShiftList();

  public JobShiftListPersistenceTest() {
    super(JobShiftList.class);
  }

  @BeforeAll public void setUp() {
    User olav =
        new User("olav", new HashedPassword("bestePassord123"), "Olav", "Nordmann");
    User nora =
        new User("nora", new HashedPassword("bestePassord123"), "Nora", "Bekkestad");

    JobShift jobShift1 = new JobShift(olav, LocalDateTime.parse("2021-10-10T17:10:53.798134"),
        Duration.ofSeconds(7200), "Cool info");
    JobShift jobShift2 =
        new JobShift(nora, LocalDateTime.now(), Duration.ofSeconds(7200), "Cool test info");
    jobShiftList.add(jobShift1);
    jobShiftList.add(jobShift2);
  }

  @Override public Object getObject() {
    return jobShiftList;
  }

}
