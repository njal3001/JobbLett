package jobblett.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JobShiftTest {

  private JobShift jobShift;

  @BeforeEach 
  public void setUp() {
    User user = new User("user", new HashedPassword("Test12345"), "Ole", "Dole");
    jobShift = new JobShift(user, LocalDateTime.now().plusHours(1), Duration.ofHours(4), "Info");
  }

  @Test 
  public void testGetEndingTime() {
    //Some tolerance was needed because of LocalDateTime.now()
    assertTrue(LocalDateTime.now().plusHours(5).plusSeconds(1).isAfter(jobShift.getEndingTime()));
    assertTrue(LocalDateTime.now().plusHours(5).minusSeconds(1).isBefore(jobShift.getEndingTime()));
  }

  @Test 
  public void testIsOutDated_notOutdated() {
    assertFalse(jobShift.isOutDated());
  }

  @Test 
  public void testIsOutDated_Outdated() {
    jobShift.setStartingTime(LocalDateTime.now().minusHours(2));
    assertTrue(jobShift.isOutDated());
  }

}
