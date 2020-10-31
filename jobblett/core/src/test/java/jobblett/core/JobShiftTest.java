package jobblett.core;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.Duration;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JobShiftTest{

private JobShift jobShift;

@BeforeEach
public void setUp(){
  User user = new User("user", HashedPassword.hashPassword("Test12345"), "Ole", "Dole");
  jobShift = new JobShift(user, LocalDateTime.now().plusHours(1), Duration.ofHours(4), "Info");
}

@Test
public void testGetEndingTime(){
  //Some tolerance was needed because of LocalDateTime.now()
  assertTrue(LocalDateTime.now().plusHours(5).plusSeconds(1).isAfter(jobShift.getEndingTime()));
  assertTrue(LocalDateTime.now().plusHours(5).minusSeconds(1).isBefore(jobShift.getEndingTime()));
}

@Test
public void testSetStartingTime_SetToThePast(){
  try{
    jobShift.setStartingTime(LocalDateTime.now().minusHours(2));
    fail("Exception should be thrown");
  } catch(Exception e){
    assertTrue(e instanceof IllegalArgumentException);
  }
}

}