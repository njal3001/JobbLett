package jobblett.json;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Iterator;
import jobblett.core.HashedPassword;
import jobblett.core.JobShift;
import jobblett.core.JobShiftList;
import jobblett.core.User;
import org.junit.jupiter.api.BeforeEach;

public class JobShiftListPersistenceTest extends AbstractPersistenceTest<JobShiftList>{

  JobShiftList jobShiftList = new JobShiftList();

  public JobShiftListPersistenceTest() {
    super(JobShiftList.class);
  }

  @BeforeEach public void setUp() {
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

  @Override public JobShiftList getObject() {
    return jobShiftList;
  }

  @Override
  public boolean isEquals(JobShiftList o1, JobShiftList o2) {
    if (o1.size() != o2.size()) {
      return false;
    }

    Iterator<JobShift> iter1 = o1.iterator();
    Iterator<JobShift> iter2 = o2.iterator();
    JobShiftPersistenceTest jobShiftPersistenceTest = new JobShiftPersistenceTest();
    
    while (iter1.hasNext()) {
      if(!jobShiftPersistenceTest.isEquals(iter1.next(), iter2.next())) {
        return false;
      }
    }
    return true;
  }

}
