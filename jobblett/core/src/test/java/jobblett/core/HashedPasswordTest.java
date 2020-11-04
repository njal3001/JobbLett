package jobblett.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HashedPasswordTest {

  @Test public void testEquals() {
    HashedPassword password = HashedPassword.hashPassword("Nils1254");
    assertTrue(password.equals(HashedPassword.hashPassword("Nils1254")));
    assertFalse(password.equals(HashedPassword.hashPassword("nilS1254")));
  }

  @Test public void testSetPassword() {
    try {
      HashedPassword.hashPassword("Nils1254");
    } catch (Exception e) {
      fail("Exception should not be thrown for this input");
    }
    try {
      HashedPassword.hashPassword("");
      fail("Exception should be thrown");
    } catch (Exception e) {
      assertTrue(e instanceof IllegalArgumentException);
    }
  }

}
