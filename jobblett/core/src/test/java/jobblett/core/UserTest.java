package jobblett.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

  @Test public void testValidUsername() {
    assertTrue(User.validUsername("njåøl3001"));
    assertTrue(User.validUsername("N2"));
    assertTrue(User.validUsername("Æ+-$3"));
    assertFalse(User.validUsername(" n2"));
    assertFalse(User.validUsername("n"));
  }

  @Test public void testValidName() {
    assertTrue(User.validName("Nils"));
    assertTrue(User.validName("nk"));
    assertTrue(User.validName("Njåøæl"));
    assertFalse(User.validName("njal3001"));
    assertFalse(User.validName(" nils"));
    assertFalse(User.validName("n"));
    assertFalse(User.validName("+-33"));
  }


  @Test public void testConstructor() {
    try {
      User u1 = new User("njal3001", new HashedPassword("Nils1254"), "Nils", "Enge");
      assertEquals(u1.getUsername(), "njal3001");
      assertEquals(u1.getGivenName(), "Nils");
      assertEquals(u1.getFamilyName(), "Enge");
    } catch (Exception e) {
      fail("Exception should not be thrown for this input");
    }
    try {
      new User("", new HashedPassword("Nils1254"), "Nils", "Enge");
      fail("Exception should be thrown because of invalid username");
    } catch (Exception e) {
      assertTrue(e instanceof IllegalArgumentException);
    }

    try {
      new User("njal3001", new HashedPassword(""), "Nils", "Enge");
      fail("Exception should be thrown because of invalid password");
    } catch (Exception e) {
      assertTrue(e instanceof IllegalArgumentException);
    }

    try {
      new User("njal3001", new HashedPassword("Nils1254"), "", "Enge");
      fail("Exception should be thrown because of invalid given name");
    } catch (Exception e) {
      assertTrue(e instanceof IllegalArgumentException);
    }

    try {
      new User("njal3001", new HashedPassword("Nils1254"), "Nils", "");
      fail("Exception should be thrown because of invalid family name");
    } catch (Exception e) {
      assertTrue(e instanceof IllegalArgumentException);
    }
  }

  @Test public void testSetName() {
    User u1 = new User("njal3001", new HashedPassword("Nils1254"), "Nils", "Enge");
    try {
      u1.setName("Kåre", "Heins");
      assertEquals(u1.getGivenName(), "Kåre");
      assertEquals(u1.getFamilyName(), "Heins");
    } catch (Exception e) {
      fail("Exception should not be thrown for this input");
    }
    try {
      u1.setName("", "");
      fail("Exception should be thrown");
    } catch (Exception e) {
      assertTrue(e instanceof IllegalArgumentException);
      assertEquals(u1.getGivenName(), "Kåre");
      assertEquals(u1.getFamilyName(), "Heins");
    }
  }

  @Test public void testToString() {
    User u1 = new User("njal3001", new HashedPassword("Nils1254"), "Nils", "Enge");
    assertEquals("Nils Enge (@njal3001)", u1.toString());
  }

}
