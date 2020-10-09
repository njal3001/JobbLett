package jobblett.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Iterator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JobShiftListTest{

  private JobShiftList jobShiftList;
  private JobShift jobShift1;
  private JobShift jobShift2;
  private JobShift jobShift3;
  private User user1;
  private User user2;


  @BeforeEach
  public void setUp(){
    jobShiftList = new JobShiftList();
    user1 = new User("user", "Test12345", "Ole", "Dole");
    user2 = new User("user2", "Test12345", "Ole", "Dole");
    jobShift1 = new JobShift(user1, LocalDateTime.now().plusDays(2), Duration.ofHours(4), "info");
    jobShift2 = new JobShift(user2, LocalDateTime.now().plusDays(3), Duration.ofHours(4), "info");
    jobShift3 = new JobShift(user1, LocalDateTime.now().plusDays(1), Duration.ofHours(4), "info");
  }

  @Test
  public void testGetJobShifts_withUserFilter(){
    jobShiftList.addJobShift(jobShift1);
    jobShiftList.addJobShift(jobShift2);
    jobShiftList.addJobShift(jobShift3);
    Collection<JobShift> filteredJobShifts = jobShiftList.getJobShifts(user2);
    assertEquals(1, filteredJobShifts.size());
    assertEquals(jobShift2, filteredJobShifts.iterator().next());
  }

  @Test
  public void testGetJobShifts_emptyList(){
    assertEquals(0, jobShiftList.getJobShifts().size());
  }

  @Test
  public void testGetJobShifts_changeJobShiftAfterAdding(){
    jobShiftList.addJobShift(jobShift1);
    jobShift1.setInfo("New info");
    assertEquals("New info", jobShiftList.getJobShifts().iterator().next().getInfo());
  }

  @Test
  public void testAddJobShift_isSorted(){
    jobShiftList.addJobShift(jobShift1);
    jobShiftList.addJobShift(jobShift2);
    jobShiftList.addJobShift(jobShift3);
    Iterator<JobShift> it = jobShiftList.iterator();
    assertTrue(it.hasNext());
    assertEquals(jobShift3, it.next());
    assertTrue(it.hasNext());
    assertEquals(jobShift1, it.next());
    assertTrue(it.hasNext());
    assertEquals(jobShift2, it.next());
    assertFalse(it.hasNext());
  }
}