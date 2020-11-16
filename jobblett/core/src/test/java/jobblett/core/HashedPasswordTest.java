package jobblett.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HashedPasswordTest {

  @Test public void testEquals() {
    HashedPassword password = new HashedPassword("Nils1254");
    HashedPassword rightPassword = new HashedPassword("Nils1254");
    HashedPassword wrongPassword = new HashedPassword("nilS1254");
    assertTrue(password.matches(rightPassword));
    assertFalse(password.matches(wrongPassword));
  }

  @Test public void testSetPassword() {
    try {
      new HashedPassword("Nils1254");
    } catch (Exception e) {
      fail("Exception should not be thrown for this input");
    }
    try {
      new HashedPassword("");
      fail("Exception should be thrown");
    } catch (Exception e) {
      assertTrue(e instanceof IllegalArgumentException);
    }
  }

}
