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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

public class JobShiftPersistenceTest extends AbstractPersistenceTest<JobShift> {

  JobShift jobShift;

  public JobShiftPersistenceTest() {
    super(JobShift.class);
  }


  @BeforeEach
  public void setUp() {
    User olav = new User("olav", new HashedPassword("bestePassord123"), "Olav", "Nordmann");
    jobShift =
        new JobShift(olav, LocalDateTime.parse("2021-10-10T17:10:53.798134"), Duration.ofSeconds(7200), "Cool info");
  }

  @Override
  public JobShift getObject() {
    return jobShift;
  }


  @Override
  public boolean isEquals(JobShift o1, JobShift o2) {
    UserPersistenceTest userPersistenceTest = new UserPersistenceTest();
    if(!userPersistenceTest.isEquals(o1.getUser(), o2.getUser())) {
      return false;
    }
    if (!o1.getStartingTime().equals(o2.getStartingTime())) {
      return false;
    }
    if (!o1.getDuration().equals(o2.getDuration())) {
      return false;
    }
    if (!o1.getInfo().equals(o2.getInfo())) {
      return false;
    }
    return true;
  }
}
