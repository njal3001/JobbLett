package jobblett.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HashedPasswordTest {

  @Test public void testEquals() {
    HashedPassword password = new HashedPassword("Nils1254");
    assertTrue(password.matches("Nils1254"));
    assertFalse(password.matches("nilS1254"));
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
